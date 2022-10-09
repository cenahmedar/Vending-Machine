package main.vendingMachines;

import main.parts.IKeyBadOnSubmit;
import main.parts.KeyBad;
import main.parts.KeyBadInput;
import main.payments.*;
import main.prodcuts.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class VendingMachine implements IVendingMachine, IKeyBadOnSubmit {
    private Map<Integer, Product> products;
    private Map<String, Integer> productsMapper;
    private int currentProductKeypadNumber = 1;
    private int insertedTotal = 0;
    private Map<Payment, Integer> insertedPayments;
    private KeyBad keyBad;
    private Product selectedProduct;
    private int change;
    private int returned;
    private CashInventory cashInventory;
    private Map<Payment, Integer> changes;
    private int remainChange;

    VendingMachine() {
        this.products = new HashMap<>();
        this.productsMapper = new HashMap<>();
        this.insertedPayments = new HashMap<>();
        this.keyBad = new KeyBad(this);

        initDefaultProducts();
        initCashInventory();
    }

    private void initCashInventory() {
        Map<Payment, Integer> payments = buildPayments();
        if (payments == null) this.cashInventory = new CashInventory();
        else this.cashInventory = new CashInventory().build(payments);
    }

    private void initDefaultProducts() {
        List<Product> products = buildProducts();
        for (Product product : products) {
            addProduct(product);
        }
    }

    protected abstract List<Product> buildProducts();

    protected abstract Map<Payment, Integer> buildPayments();

    @Override
    public void addProduct(Product product) {
        if (productsMapper.containsKey(product.getName())) {
            updateExistProduct(product);
        } else {
            products.put(currentProductKeypadNumber, product);
            productsMapper.put(product.getName(), currentProductKeypadNumber);
            currentProductKeypadNumber++;
        }
    }

    // if the product already exist in the machine add new quantity
    // and update price to max
    private void updateExistProduct(Product product) {
        int productPosition = productsMapper.get(product.getName());
        Product oldProduct = products.get(productPosition);
        oldProduct.addQuantity(product.getQuantity());
        int maxPrice = oldProduct.getPrice() > product.getPrice() ? oldProduct.getPrice() : product.getPrice();
        oldProduct.setPrice(maxPrice);
        products.put(productPosition, oldProduct);
    }


    @Override
    public void insertPayment(PaymentMethod paymentMethod) {
        if (!this.validatePayment(paymentMethod)) return;

        addNewPaymentAmount(paymentMethod);
        saveNewPayment(paymentMethod);

        printTotalAmount();
        boolean dispenseProduct = checkIfAmountIsEnough();
        if (!dispenseProduct) return;
        dispensesProduct();
        returnChange();
    }

    @Override
    public boolean validatePayment(PaymentMethod paymentMethod) {
        if (this.selectedProduct == null) {
            System.out.println("Please select product first");
            return false;
        }
        if (!paymentMethod.isValid()) {
            System.out.println("Invalid payment");
            System.out.println("$" + getAmountInUSD(paymentMethod.payment.amount()) + " Returned");
            return false;
        }
        return true;
    }

    private void addNewPaymentAmount(PaymentMethod paymentMethod) {
        if (paymentMethod.payment == Card.VISA) {
            this.insertedTotal = selectedProduct.getPrice();
        } else {
            this.insertedTotal += paymentMethod.getAmount();
        }
    }

    private void saveNewPayment(PaymentMethod paymentMethod) {
        if (this.insertedPayments.containsKey(paymentMethod.payment)) {
            this.insertedPayments.put(paymentMethod.payment, insertedPayments.get(paymentMethod.payment) + 1);
        } else {
            this.insertedPayments.put(paymentMethod.payment, 1);
        }
    }

    @Override
    public void dispensesProduct() {
        System.out.println("Please pick your " + selectedProduct.getName());
    }

    @Override
    public void returnChange() {
        int change = this.insertedTotal - this.selectedProduct.getPrice();
        this.change = change;
        this.changes = new HashMap<>();

        cashInventory.buildForChange(this.insertedPayments);

        for (Note note : Note.values()) {
            change = getChange(note, change);
        }

        for (Coin coin : Coin.values()) {
            change = getChange(coin, change);
        }
        if (change != 0) {
            this.remainChange = change;
            System.out.println("There is no enough change");
            keyBadCancelClick();
            return;
        }

        System.out.println("Your Total Change is $" + getAmountInUSD(this.change));

        changes.forEach((key, value) -> {
            System.out.println(value + " $" + getAmountInUSD(key.amount()) + " Dispensed");
            cashInventory.deduct(key, value);
        });

        reset();
    }

    private int getChange(Payment payment, int change) {
        int numberOfDispenses = change / payment.amount();
        if (numberOfDispenses == 0) return change;
        int paymentQuantity = this.cashInventory.getQuantity(payment);
        numberOfDispenses = paymentQuantity >= numberOfDispenses ? numberOfDispenses : numberOfDispenses - paymentQuantity;
        if (numberOfDispenses == 0) return change;
        changes.put(payment, numberOfDispenses);
        return change - (numberOfDispenses * payment.amount());
    }

    private void printTotalAmount() {
        System.out.println("Total Entered Amount is $" + getAmountInUSD(insertedTotal));
    }

    private boolean checkIfAmountIsEnough() {
        if (insertedTotal >= selectedProduct.getPrice()) return true;
        System.out.println("You need $" + getAmountInUSD(selectedProduct.getPrice() - insertedTotal) + " more");
        return false;
    }

    @Override
    public void pressKey(KeyBadInput keyBadInput) {
        keyBad.pressKey(keyBadInput);
    }

    @Override
    public void keyBadSubmitClick(int selectedItem) {
        if (!this.products.containsKey(selectedItem)) {
            System.out.println("Product is not available");
            return;
        }
        Product product = this.products.get(selectedItem);
        if (product.getQuantity() == 0) {
            System.out.println("Product is not available");
            return;
        }

        System.out.println("Selected product Price is $" + getAmountInUSD(product.getPrice()));
        this.selectedProduct = product;
    }

    @Override
    public void keyBadCancelClick() {
        this.selectedProduct = null;

        insertedPayments.forEach((key, value) -> {
            System.out.println(value + " $" + getAmountInUSD(key.amount()) + " Returned");
            this.returned += key.amount();
        });
    }

    @Override
    public void reset() {
        this.selectedProduct = null;
        this.cashInventory.reset();
    }

    private BigDecimal getAmountInUSD(int amount) {
        return new BigDecimal(amount).movePointLeft(2);
    }

    public String getSelectedProductName() {
        if (selectedProduct == null) return null;
        return selectedProduct.getName();
    }

    public int getInsertedTotal() {
        return this.insertedTotal;
    }

    public int getChange() {
        return this.change;
    }

    public int getRemainChange() {
        return this.remainChange;
    }

    public int getReturned() {
        return this.returned;
    }
}

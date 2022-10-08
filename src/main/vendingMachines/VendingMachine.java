package main.vendingMachines;

import main.parts.IKeyBadOnSubmit;
import main.parts.KeyBad;
import main.parts.KeyBadInput;
import main.payments.Note;
import main.payments.Payment;
import main.payments.PaymentMethod;
import main.prodcuts.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class VendingMachine implements IVendingMachine, IKeyBadOnSubmit {
    //Integer: product keypad number
    private Map<Integer, Product> products;
    // holds product name and keypad number
    private Map<String, Integer> productsMapper;
    private int currentProductKeypadNumber = 1;
    private int insertedTotal = 0;
    private Map<Payment, Integer> insertedPayments;
    private KeyBad keyBad;
    private Product selectedProduct;

    VendingMachine() {
        this.products = new HashMap<>();
        this.productsMapper = new HashMap<>();
        this.insertedPayments = new HashMap<>();
        this.keyBad = new KeyBad(this);
    }

    @Override
    public void insertPayment(PaymentMethod paymentMethod) {
        if (!paymentMethod.isValid()) {
            System.out.println("Invalid payment");
            System.out.println("$" + getAmountInUSD(paymentMethod.payment.amount()) + " Returned");
            return;
        }
        this.insertedTotal += paymentMethod.getAmount();

        if (this.insertedPayments.containsKey(paymentMethod.payment)) {
            this.insertedPayments.put(paymentMethod.payment, insertedPayments.get(paymentMethod.payment) + 1);
        } else {
            this.insertedPayments.put(paymentMethod.payment, 1);
        }


        printTotalAmount();
        boolean dispenseProduct = checkIfAmountIsEnough();
        if (!dispenseProduct) return;
        dispensesProduct();
        returnChange();
    }

    @Override
    public void dispensesProduct() {
        System.out.println("Please pick your " + selectedProduct.getName());
    }

    @Override
    public void returnChange() {
        int change = this.insertedTotal - this.selectedProduct.getPrice();
        System.out.println("Change is $" + getAmountInUSD(change));
        for (Note note : Note.values()) {
            change = printChange(note, change);
        }
    }

    private int printChange(Payment payment, int change) {
        int numberOfDispenses = change / payment.amount();
        if (numberOfDispenses == 0) return change;
        System.out.println(numberOfDispenses + " $" + getAmountInUSD(payment.amount()) + " Dispensed");
        return numberOfDispenses * payment.amount();
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
    public void addProduct(Product product) {
        if (productsMapper.containsKey(product.getName())) {
            updateExistProduct(product);
        } else {
            products.put(currentProductKeypadNumber, product);
            productsMapper.put(product.getName(), currentProductKeypadNumber);
            currentProductKeypadNumber++;
        }
    }

    @Override
    public void pressKey(KeyBadInput keyBadInput) {
        keyBad.pressKey(keyBadInput);
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
        insertedPayments.forEach((key, value) -> System.out.println(value + " $" + getAmountInUSD(key.amount()) + " Returned"));
    }

    private BigDecimal getAmountInUSD(int amount) {
        return new BigDecimal(amount).movePointLeft(2);
    }
}

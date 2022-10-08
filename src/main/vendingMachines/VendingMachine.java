package main.vendingMachines;

import main.parts.IKeyBadOnSubmit;
import main.parts.KeyBad;
import main.parts.KeyBadInput;
import main.payments.Payment;
import main.payments.PaymentMethod;
import main.prodcuts.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class VendingMachine implements IVendingMachine, IKeyBadOnSubmit {
    //Integer: product keypad number
    private Map<Integer, Product> products;
    // holds product name and keypad number
    private Map<String, Integer> productsMapper;
    private int currentProductKeypadNumber = 1;
    private int insertedTotal = 0;
    private Set<Payment> insertedPayments;
    private KeyBad keyBad;
    private Product selectedProduct;

    VendingMachine() {
        this.products = new HashMap<>();
        this.productsMapper = new HashMap<>();
        this.insertedPayments = new HashSet<>();
        this.keyBad = new KeyBad(this);
    }

    @Override
    public void insertPayment(PaymentMethod payment) {
        if (!payment.isValid()) {
            System.out.println("Invalid payment");
            return;
        }
        this.insertedTotal += payment.getAmount();
        this.insertedPayments.add(payment.payment);
        printTotalAmount();
        checkIfAmountIsEnough();
    }

    private void printTotalAmount() {
        BigDecimal payment = new BigDecimal(insertedTotal).movePointLeft(2);
        System.out.println("Total Entered Amount is $" + payment);
    }

    private void checkIfAmountIsEnough() {
        if (insertedTotal < selectedProduct.getPrice()) {
            BigDecimal remainAmount = new BigDecimal(selectedProduct.getPrice() - insertedTotal).movePointLeft(2);
            System.out.println("You need $" + remainAmount + " more");
            return;
        }
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
    public void keyBadSubmit(int selectedItem) {
        if (!this.products.containsKey(selectedItem)) {
            System.out.println("Product is not available");
            return;
        }
        Product product = this.products.get(selectedItem);
        if (product.getQuantity() == 0) {
            System.out.println("Product is not available");
            return;
        }

        BigDecimal price = new BigDecimal(product.getPrice()).movePointLeft(2);
        System.out.println("Selected product Price is $" + price);
        this.selectedProduct = product;
    }
}

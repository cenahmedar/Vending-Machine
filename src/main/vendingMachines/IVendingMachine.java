package main.vendingMachines;

import main.parts.KeyBadInput;
import main.payments.PaymentMethod;
import main.prodcuts.Product;

interface IVendingMachine {
    void addProduct(Product product);
    void pressKey(KeyBadInput keyBadInput);
    void insertPayment(PaymentMethod paymentMethod);
    boolean validatePayment(PaymentMethod paymentMethod);
    void dispensesProduct();
    void returnChange();
    void reset();
}

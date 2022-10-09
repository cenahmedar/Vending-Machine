package main.vendingMachines;

import main.payments.Coin;
import main.payments.Note;
import main.payments.Payment;
import main.prodcuts.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnackMachine extends VendingMachine {

    @Override
    protected List<Product> buildProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Cheetos", 12000, 3));
        products.add(new Product("Snickers", 6000, 5));
        products.add(new Product("Pringles", 2015, 6));
        products.add(new Product("Oreos", 15000, 4));
        return products;
    }

    @Override
    protected Map<Payment, Integer> buildPayments() {
        Map<Payment, Integer> payments = new HashMap<>();
        payments.put(Note.D50, 1);
        payments.put(Note.D20, 1);
        payments.put(Coin.D1, 100);
        payments.put(Coin.C20, 3);
        return payments;
    }

}

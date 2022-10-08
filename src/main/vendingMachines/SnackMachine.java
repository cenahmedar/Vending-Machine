package main.vendingMachines;

import main.prodcuts.Product;

public class SnackMachine extends VendingMachine {

    public SnackMachine() {
        addSnacks();
    }

    private void addSnacks() {
        this.addProduct(new Product("Cheetos", 12000, 3));
        this.addProduct(new Product("Snickers", 8000, 5));
        this.addProduct(new Product("Pringles", 23000, 6));
        this.addProduct(new Product("Oreos", 15000, 4));
    }
}

package main.payments;

public enum Coin implements Payment {
    C10(10),
    C20(20),
    C50(50),
    D1(100);

    private int amountInCent;

    Coin(int amountInCent) {
        this.amountInCent = amountInCent;
    }

    @Override
    public int amount() {
        return amountInCent;
    }
}
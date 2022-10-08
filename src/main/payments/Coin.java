package main.payments;

public enum Coin implements Payment {
    D1(100),
    C50(50),
    C20(20),
    C10(10);

    private int amountInCent;

    Coin(int amountInCent) {
        this.amountInCent = amountInCent;
    }

    @Override
    public int amount() {
        return amountInCent;
    }
}
package main.payments;

public enum Note implements Payment {
    D20(2000),
    D50(5000);

    private int amountInCent;

    Note(int amountInCent) {
        this.amountInCent = amountInCent;
    }

    @Override
    public int amount() {
        return amountInCent;
    }
}

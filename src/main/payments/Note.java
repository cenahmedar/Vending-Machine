package main.payments;

public enum Note implements Payment {
    D20("$20", 2000),
    D50("$50", 5000);

    private int amountInCent;
    private String name;

    Note(String name, int amountInCent) {
        this.amountInCent = amountInCent;
        this.name = name;
    }

    @Override
    public int amount() {
        return amountInCent;
    }

    @Override
    public String toString() {
        return name;
    }
}

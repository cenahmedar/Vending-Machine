package main.payments;

public enum Card implements Payment {
    VISA;

    @Override
    public int amount() {
        return 0;
    }
}
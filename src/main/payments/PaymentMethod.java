package main.payments;

import main.parts.Slot;

public abstract class PaymentMethod<T extends Payment> {
    Slot slot;
    public T payment;

    PaymentMethod(T payment) {
        this.payment = payment;
        this.slot = getSlot();
    }

    public abstract Slot getSlot();

    public abstract boolean isValid();

    public int getAmount() {
        return payment.amount();
    }

    public String getName() {
        return payment.toString();
    }
}
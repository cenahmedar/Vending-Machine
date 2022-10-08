package main.payments;

import main.parts.CardSlot;
import main.parts.Slot;

public class CardPaymentMethod extends PaymentMethod {

    public CardPaymentMethod(Payment payment) {
        super(payment);
    }

    @Override
    public boolean isValid() {
        return this.slot.validatePayment(this);
    }

    @Override
    public Slot getSlot() {
        return new CardSlot();
    }
}
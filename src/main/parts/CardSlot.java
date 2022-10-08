package main.parts;

import main.payments.PaymentMethod;

public class CardSlot implements Slot {

    @Override
    public boolean validatePayment(PaymentMethod payment) {
        return true;
    }
}
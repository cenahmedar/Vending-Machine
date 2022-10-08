package main.payments;

import main.parts.CoinSlot;
import main.parts.Slot;

public class CoinPaymentMethod extends PaymentMethod {

    public CoinPaymentMethod(Payment payment) {
        super(payment);
    }

    @Override
    public boolean isValid() {
        return this.slot.validatePayment(this);
    }

    @Override
    public Slot getSlot() {
        return new CoinSlot();
    }
}
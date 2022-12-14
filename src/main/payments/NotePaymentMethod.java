package main.payments;

import main.parts.NoteSlot;
import main.parts.Slot;

public class NotePaymentMethod extends PaymentMethod {

    public NotePaymentMethod(Payment payment) {
        super(payment);
    }

    @Override
    public boolean isValid() {
        return this.slot.validatePayment(this);
    }

    @Override
    public Slot getSlot() {
        return new NoteSlot();
    }
}

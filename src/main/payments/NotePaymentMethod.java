package main.payments;

import main.parts.NoteSlot;
import main.parts.Slot;

public class NotePaymentMethod extends PaymentMethod<Note> {

    public NotePaymentMethod(Note payment) {
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

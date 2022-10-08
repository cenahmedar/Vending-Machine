package main.parts;

import main.payments.Note;
import main.payments.PaymentMethod;

public class NoteSlot implements Slot {

    @Override
    public boolean validatePayment(PaymentMethod payment) {
        return payment.payment == Note.D50 || payment.payment == Note.D20;
    }
}

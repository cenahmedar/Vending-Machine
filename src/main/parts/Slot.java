package main.parts;

import main.payments.PaymentMethod;

public interface Slot {

    boolean validatePayment(PaymentMethod payment);
}

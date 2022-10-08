package main.parts;

import main.payments.Coin;
import main.payments.PaymentMethod;

public class CoinSlot implements Slot {

    @Override
    public boolean validatePayment(PaymentMethod payment) {
        return payment.payment == Coin.C10 ||
                payment.payment == Coin.C20 ||
                payment.payment == Coin.C50 ||
                payment.payment == Coin.D1;
    }
}
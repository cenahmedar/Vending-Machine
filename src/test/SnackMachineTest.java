package test;

import main.parts.KeyBadInput;
import main.payments.*;
import main.vendingMachines.SnackMachine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SnackMachineTest {

    @Test
    public void selectItem() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.pressKey(KeyBadInput.TOW);
        snackMachine.pressKey(KeyBadInput.SUBMIT);
        assertEquals(snackMachine.getSelectedProductName(), "Snickers");
    }

    @Test
    public void insertNote() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.pressKey(KeyBadInput.TOW);
        snackMachine.pressKey(KeyBadInput.SUBMIT);
        snackMachine.insertPayment(new NotePaymentMethod(Note.D20));
        snackMachine.insertPayment(new NotePaymentMethod(Note.D50));
        assertEquals(snackMachine.getInsertedTotal(), 7000);
    }

    @Test
    public void insertCoin() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.pressKey(KeyBadInput.TOW);
        snackMachine.pressKey(KeyBadInput.SUBMIT);
        snackMachine.insertPayment(new CoinPaymentMethod(Coin.D1));
        assertEquals(snackMachine.getInsertedTotal(), 100);
    }

    @Test
    public void insertCard() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.pressKey(KeyBadInput.TOW);
        snackMachine.pressKey(KeyBadInput.SUBMIT);
        snackMachine.insertPayment(new CardPaymentMethod(Card.VISA));
        assertEquals(snackMachine.getInsertedTotal(), 6000);
    }

    @Test
    public void change() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.pressKey(KeyBadInput.TOW);
        snackMachine.pressKey(KeyBadInput.SUBMIT);
        snackMachine.insertPayment(new NotePaymentMethod(Note.D20));
        snackMachine.insertPayment(new NotePaymentMethod(Note.D50));
        assertEquals(snackMachine.getChange(), 1000);
    }

    @Test
    public void cancel() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.pressKey(KeyBadInput.TOW);
        snackMachine.pressKey(KeyBadInput.SUBMIT);
        snackMachine.insertPayment(new NotePaymentMethod(Note.D20));
        snackMachine.pressKey(KeyBadInput.CANCEL);
        assertEquals(snackMachine.getReturned(), 2000);
    }
}

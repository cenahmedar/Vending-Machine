package main;

import main.parts.KeyBadInput;
import main.payments.Note;
import main.payments.NotePaymentMethod;
import main.vendingMachines.SnackMachine;

public class Main {

    public static void main(String[] args) {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.pressKey(KeyBadInput.TOW);
        snackMachine.pressKey(KeyBadInput.SUBMIT);
        snackMachine.insertPayment(new NotePaymentMethod(Note.D20));
        snackMachine.insertPayment(new NotePaymentMethod(Note.D50));
    }
}

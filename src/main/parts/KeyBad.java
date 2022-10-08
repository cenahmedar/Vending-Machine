package main.parts;

public class KeyBad {
    private final IKeyBadOnSubmit keyBadOnSubmit;
    private String selection;

    public KeyBad(IKeyBadOnSubmit keyBadOnSubmit) {
        this.selection = "";
        this.keyBadOnSubmit = keyBadOnSubmit;
    }

    public void pressKey(KeyBadInput keyBadInput) {
        if (keyBadInput == KeyBadInput.DELETE) {
            selection = selection.length() > 1 ? selection.substring(0, selection.length() - 1) : selection;
            return;
        }
        if (keyBadInput == KeyBadInput.SUBMIT) {
            if (selection.isEmpty()) {
                System.out.println("Please enter product number");
                return;
            }
            keyBadOnSubmit.keyBadSubmit(Integer.parseInt(selection));
            return;
        }
        selection += keyBadInput.getValue();
    }
}

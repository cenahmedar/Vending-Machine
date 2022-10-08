package main.parts;

public enum KeyBadInput {
    ONE(1),
    TOW(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    ZERO(0),
    DELETE(-1),
    SUBMIT(-1),
    CANCEL(-1);

    private final int value;

    KeyBadInput(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

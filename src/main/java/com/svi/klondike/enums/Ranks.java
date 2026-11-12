package com.svi.klondike.enums;

public enum Ranks {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13);

    private final String symbol;

    private final int value;

    Ranks(String symbol, int value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getValue() {
        return this.value;
    }

    public static Ranks getSymbol(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Error: Ranks cannot be null.");
        }

        for (Ranks rank : values()) {
            if (rank.symbol.equals(text)) {
                return rank;
            }
        }

        throw new IllegalArgumentException("Error: Invalid rank symbol" + text);
    }

}

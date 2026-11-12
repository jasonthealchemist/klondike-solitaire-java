package com.svi.klondike.enums;

public enum Suits {
    DIAMOND('D', SuitColor.RED, "♦"),
    HEART('H', SuitColor.RED, "♥"),
    SPADE('S', SuitColor.BLACK, "♠"),
    CLUBS('C', SuitColor.BLACK, "♣");

    private static final int SYMBOL_LENGTH = 1;

    private final char symbol;
    private final SuitColor color;
    private final String displaySymbol;

    Suits(char symbol, SuitColor color, String displaySymbol){
        this.symbol = symbol;
        this.color = color;
        this.displaySymbol = displaySymbol;
    }

    public char getSymbol(){return symbol;}

    public SuitColor getColor(){return color;}

    public String getDisplaySymbol(){return displaySymbol;}

    public static Suits fromSymbol(String text){
        if(text == null){
            throw new IllegalArgumentException("Error: Suit symbol cannot be null.");
        }

        if (text.length() != SYMBOL_LENGTH) {
            throw new IllegalArgumentException("Error: Suit symbol must be exactly " + SYMBOL_LENGTH + " character long.");
        }

        for (Suits suits : values()){
            if(suits.symbol == text.charAt(0)){
                return suits;
            }
        }
        throw new IllegalArgumentException("Error: Invalid suit symbol: " + text);
    }
}
package com.svi.klondike.enums;

public enum Suits {
    DIAMOND('D', "RED"),
    HEART('H', "RED"),
    SPADE('S', "BLACK"),
    CLUBS('C', "BLACK");

    private static final int SYMBOL_LENGTH = 1;

    private final char symbol;
    private final String color;


    Suits(char symbol,String color){
        this.symbol = symbol;
        this.color = color;
    }

    public char getSymbol(){return symbol;}

    public String getColor(){return color;}

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
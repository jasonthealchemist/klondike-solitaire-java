package com.svi.klondike.config;

public enum DrawMode {
    ONE(1, "Draw one card"),
    THREE(3, "Draw three card");

    private final int cardCount;
    private final String label;

    DrawMode(int cardCount, String label){
        this.cardCount = cardCount;
        this.label = label;
    }

    public int getCardCount(){
        return cardCount;
    }

    public String getLabel(){
        return label;
    }
}

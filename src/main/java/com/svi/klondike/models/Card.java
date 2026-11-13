package com.svi.klondike.models;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.enums.SuitColor;
import com.svi.klondike.enums.Suits;

public class Card {
    private final Ranks rank;
    private final Suits suit;
    private boolean faceUp;
    private static final String FACE_DOWN_TEXT = "???";
    private static final String SYMBOL_SEPARATOR = "-";
    private static final String DISPLAY_SEPARATOR = " ";

    public Card(Ranks rank, Suits suit){
        this.rank = rank;
        this.suit = suit;
        this.faceUp = false;
    }

    public Ranks getRank(){
        return rank;
    }

    public boolean isFaceUp(){
        return faceUp;
    }

    public void turnFaceUp(){
        faceUp = true;
    }

    public void turnFaceDown(){
        faceUp = false;
    }

    public boolean isRed(){
        return suit.getColor() == SuitColor.RED;
    }

    public boolean isOppositeColorTo(Card other){
        return suit.getColor() != other.suit.getColor();
    }

    public boolean isSameSuitAs(Card other){
        return suit == other.suit;
    }

    public boolean isOneRankLowerThan(Card other){
        return rank.getValue() == other.rank.getValue() - 1;
    }

    @Override
    public String toString() {
        if (!faceUp) {
            return FACE_DOWN_TEXT;
        }
        return suit.getDisplaySymbol() + DISPLAY_SEPARATOR + suit.getSymbol() + SYMBOL_SEPARATOR + rank.getSymbol();
    }
}

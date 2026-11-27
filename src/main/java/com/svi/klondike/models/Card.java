package com.svi.klondike.models;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.enums.Suits;

import java.util.Objects;

/**
 * One playing card: a rank, a suit, and whether it is face up. Two cards are equal when they have the same
 * rank and suit, whichever way they are facing. The natural order is by rank, then by suit.
 */
public class Card implements Comparable<Card> {
    private static final String FACE_DOWN_TEXT = "???";
    private static final String SYMBOL_SEPARATOR = "-";
    private static final String DISPLAY_SEPARATOR = " ";

    private final Ranks rank;
    private final Suits suit;
    private boolean faceUp;

    /** Creates a face-down card. */
    public Card(Ranks rank, Suits suit) {
        this.rank = Objects.requireNonNull(rank, "rank");
        this.suit = Objects.requireNonNull(suit, "suit");
        this.faceUp = false;
    }

    public Ranks getRank() {
        return rank;
    }

    public Suits getSuit() {
        return suit;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void turnFaceUp() {
        faceUp = true;
    }

    public void turnFaceDown() {
        faceUp = false;
    }

    public boolean isOppositeColorTo(Card other) {
        return suit.getColor() != other.suit.getColor();
    }

    /** True if this card's rank is exactly one below the other card's rank. */
    public boolean isOneRankLowerThan(Card other) {
        return rank.getValue() == other.rank.getValue() - 1;
    }

    /** The card written the way a deck file writes it, such as "D-A" or "H-10". */
    public String getCode() {
        return suit.getSymbol() + SYMBOL_SEPARATOR + rank.getSymbol();
    }

    /** Orders cards by rank first, then by suit. */
    @Override
    public int compareTo(Card other) {
        int byRank = rank.compareTo(other.rank);

        return (byRank != 0) ? byRank : suit.compareTo(other.suit);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card card = (Card) other;

        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    /** The card as shown on screen, such as "♦ D-A", or "???" when it is face down. */
    @Override
    public String toString() {
        if (!faceUp) {
            return FACE_DOWN_TEXT;
        }

        return DISPLAY_SEPARATOR + getCode();
    }
}

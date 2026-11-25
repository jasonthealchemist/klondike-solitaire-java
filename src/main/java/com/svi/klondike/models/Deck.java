package com.svi.klondike.models;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.enums.Suits;
import com.svi.klondike.shuffle.Shuffler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/** A 52-card deck. The top of the deck is the last card in the list. */
public class Deck {
    public static final int TOTAL_CARDS = Ranks.values().length * Suits.values().length;

    private final List<Card> cards = new ArrayList<>();

    /** Creates a standard deck, ordered suit by suit and from Ace to King within each suit. */
    public Deck() {
        for (Suits suit : Suits.values()) {
            for (Ranks rank : Ranks.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Creates a deck with the given cards, in the given order (the last card is the top).
     *
     * @throws IllegalArgumentException if there are not exactly 52 different cards
     */
    public Deck(List<Card> orderedCards) {
        if (orderedCards.size() != TOTAL_CARDS || new HashSet<>(orderedCards).size() != TOTAL_CARDS) {
            throw new IllegalArgumentException("A deck needs exactly " + TOTAL_CARDS + " different cards");
        }

        cards.addAll(orderedCards);
    }

    /** Puts the cards in a new order using the given shuffler. */
    public void shuffleWith(Shuffler shuffler) {
        shuffler.shuffle(cards);
    }

    /** Removes and returns the top card. */
    public Card dealTop() {
        return cards.remove(cards.size() - 1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    /** A copy of the current card order, bottom card first. Changing the copy does not change the deck. */
    public List<Card> getOrder() {
        return new ArrayList<>(cards);
    }
}

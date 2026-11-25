package com.svi.klondike.piles;

import com.svi.klondike.models.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A stack of cards. The top of the pile is the last card in the list.
 * Subclasses decide which cards they accept ({@link #canAccept}) and, where they differ, how runs of cards are
 * given and received.
 */
public abstract class Pile {
    protected static final int SINGLE_CARD = 1;

    private final List<Card> cards = new ArrayList<>();

    public abstract boolean canAccept(Card card);

    public boolean canAcceptRun(Card firstCard, int count) {
        return count == SINGLE_CARD && canAccept(firstCard);
    }

    public boolean canGive(int count) {
        return count == SINGLE_CARD && !isEmpty();
    }

    public void onCardsRemoved() {
        // Nothing to do by default.
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    /** The top card, or null if the pile is empty. */
    public Card peekTop() {
        if (isEmpty()) {
            return null;
        }

        return cards.get(cards.size() - 1);
    }

    /** The card that is count cards down from the top (1 is the top card). */
    public Card peekFromTop(int count) {
        return cards.get(cards.size() - count);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addCards(List<Card> newCards) {
        cards.addAll(newCards);
    }

    public Card removeTop() {
        return cards.remove(cards.size() - 1);
    }

    /** Removes the top cards and returns them in their original order. */
    public List<Card> removeTop(int count) {
        int start = cards.size() - count;

        // Copy first: subList is only a view, and clearing it would empty the copy too.
        List<Card> removed = new ArrayList<>(cards.subList(start, cards.size()));
        cards.subList(start, cards.size()).clear();

        return removed;
    }

    /** A read-only view of the cards, bottom card first, for displaying. */
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}

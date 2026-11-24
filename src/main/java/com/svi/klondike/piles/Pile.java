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

    /** The rule of this pile: may this card be placed on it? */
    public abstract boolean canAccept(Card card);

    /** May a run of cards, starting with firstCard, be placed here? By default only a single card may. */
    public boolean canAcceptRun(Card firstCard, int count) {
        return count == SINGLE_CARD && canAccept(firstCard);
    }

    /** May this many cards be taken from the top? By default only one card, and only if the pile is not empty. */
    public boolean canGive(int count) {
        return count == SINGLE_CARD && !isEmpty();
    }

    /** Called after cards were taken from this pile. By default nothing happens. */
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

    /** Removes and returns the top card. */
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

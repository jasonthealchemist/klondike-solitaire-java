package com.svi.klondike.shuffle;

import com.svi.klondike.models.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class FaroShuffler implements Shuffler {
    private final int times;

    /** @param times how many times the shuffle is repeated */
    protected FaroShuffler(int times) {
        this.times = times;
    }

    @Override
    public void shuffle(List<Card> cards) {
        for (int count = 0; count < times; count++) {
            shuffleOnce(cards);
        }
    }

    private void shuffleOnce(List<Card> cards) {
        int middle = cards.size() / 2;

        // The copies are required: the list is cleared below, and a subList is only a view of it.
        List<Card> firstHalf = new ArrayList<>(cards.subList(0, middle));
        List<Card> secondHalf = new ArrayList<>(cards.subList(middle, cards.size()));

        cards.clear();

        for (int index = 0; index < middle; index++) {
            interleave(firstHalf.get(index), secondHalf.get(index), cards);
        }

        // An odd-sized list has one extra card in the second half. Keep it so no card is ever lost.
        if (secondHalf.size() > firstHalf.size()) {
            cards.add(secondHalf.get(secondHalf.size() - 1));
        }
    }

    /** Adds one card from each half to the result, in the order that defines this kind of Faro shuffle. */
    protected abstract void interleave(Card fromFirstHalf, Card fromSecondHalf, List<Card> result);
}
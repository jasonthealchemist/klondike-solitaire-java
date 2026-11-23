package com.svi.klondike.shuffle;

import com.svi.klondike.models.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class FaroShuffler implements Shuffler {
    private final int times;

    protected  FaroShuffler(int times){
        this.times = times;
    }

    @Override
    public void shuffle(List<Card> cards){
        shuffleOnce(cards);
    }

    private void shuffleOnce(List<Card> cards){
        int middle = cards.size() / 2;

        List<Card> firstHalf = new ArrayList<>(cards.subList(0, middle));
        List<Card> secondHalf = new ArrayList<>(cards.subList(middle, cards.size()));

        cards.clear();

        for(int index = 0; index < middle; index++){
            interleave(firstHalf.get(index), secondHalf.get(index), cards);
        }
    }

    /** This abstract method adds one card from each half to the result, in the order that defines this kind of Faro shuffle. */
    protected abstract void interleave(Card fromFirstHalf, Card fromSecondHalf, List<Card> result);
}

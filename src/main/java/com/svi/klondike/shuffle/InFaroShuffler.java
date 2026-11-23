package com.svi.klondike.shuffle;

import com.svi.klondike.models.Card;

import java.util.List;

public class InFaroShuffler extends FaroShuffler{

    public InFaroShuffler(int times){
        super(times);
    }

    @Override
    protected void interleave(Card fromFirstHalf, Card fromSecondHalf, List<Card> result){
        result.add(fromSecondHalf);
        result.add(fromFirstHalf);
    }
}

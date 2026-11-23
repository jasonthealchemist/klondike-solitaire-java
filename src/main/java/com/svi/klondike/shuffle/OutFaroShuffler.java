package com.svi.klondike.shuffle;

import com.svi.klondike.models.Card;

import java.util.List;

public class OutFaroShuffler extends FaroShuffler{

    public OutFaroShuffler(int times){
        super(times);
    }

    @Override
    protected void interleave(Card fromFirstHalf, Card fromSecondHalf, List<Card> result){
        result.add(fromFirstHalf);
        result.add(fromSecondHalf);
    }
}

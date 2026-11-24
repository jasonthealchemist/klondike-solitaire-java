package com.svi.klondike.shuffle;

import com.svi.klondike.models.Card;

import java.util.List;

public class NoShuffler implements  Shuffler{

    @Override
    public void shuffle(List<Card> cards){
        // Nothing to do here;
    }
}

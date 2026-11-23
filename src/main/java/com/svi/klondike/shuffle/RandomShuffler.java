package com.svi.klondike.shuffle;

import com.svi.klondike.models.Card;
import com.svi.klondike.models.Shuffler;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomShuffler implements Shuffler {
    private final Random random;

    public RandomShuffler(Random random){
        this.random = random;
    }

    @Override
    public void shuffle(List<Card> cards){
        Collections.shuffle(cards, random);
    }
}

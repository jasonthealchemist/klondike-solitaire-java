package com.svi.klondike.shuffle;

import com.svi.klondike.config.ShuffleType;

import java.util.Random;

public class ShufflerFactory {
    private final Random random;

    public ShufflerFactory(Random random){
        this.random = random;
    }

    public Shuffler create(ShuffleType type, int shuffleCount){
        switch(type){
            case FARO_OUT -> {
                return new OutFaroShuffler(shuffleCount);
            }
            case FARO_IN -> {
                return new InFaroShuffler(shuffleCount);
            }
            default -> {
                return new RandomShuffler(random);
            }
        }
    }
}

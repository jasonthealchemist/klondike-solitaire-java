package com.svi.klondike.shuffle;

import com.svi.klondike.config.ShuffleType;

import java.util.Random;

public class ShufflerFactory {
    private final Random random;

    public ShufflerFactory(Random random){
        this.random = random;
    }

    public Shuffler create(ShuffleType type, int shuffleCount) {
        return switch (type) {
            case RANDOM -> new RandomShuffler(random);
            case FARO_OUT -> new OutFaroShuffler(shuffleCount);
            case FARO_IN -> new InFaroShuffler(shuffleCount);
            case NONE -> new NoShuffler();
        };
    }
}

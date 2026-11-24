package com.svi.klondike.config;

public record GameConfig(ShuffleType shuffleType, int shuffleCount, DrawMode drawMode, int gameCount) {
    private static final int ONE_SHUFFLE = 1;

    public GameConfig{
        if (shuffleCount < 1 || gameCount < 1){
            throw new IllegalArgumentException("Shuffle count and game must be at least between 1.");
        }
    }

    public boolean isSingleGame() {
        return gameCount == 1;
    }

    public int shuffleCountFor(int gameNumber) {
        if (!shuffleType.isFaro()) {
            return ONE_SHUFFLE;
        }
        return isSingleGame() ? shuffleCount : gameNumber;
    }
}

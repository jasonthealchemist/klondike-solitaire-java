package com.svi.klondike.config;

public class GameConfig {
    private static final int SINGLE_GAME = 1;
    private static final int RANDOM_SHUFFLE_COUNT = 1;


    private final ShuffleType shuffleType;
    private final int shuffleCount;
    private final DrawMode drawMode;
    private final int gameCount;


    public GameConfig(ShuffleType shuffleType, int shuffleCount, DrawMode drawMode, int gameCount) {
        if(shuffleCount < 1 || gameCount < 1){
            throw new IllegalArgumentException("Shuffle count and game count should be at least 1");
        }

        this.shuffleType = shuffleType;
        this.shuffleCount = shuffleCount;
        this.drawMode = drawMode;
        this.gameCount = gameCount;
    }

    public ShuffleType getShuffleType() {
        return shuffleType;
    }

    public int getShuffleCount() {
        return shuffleCount;
    }

    public DrawMode getDrawMode() {
        return drawMode;
    }

    public int getGameCount() {
        return gameCount;
    }

}

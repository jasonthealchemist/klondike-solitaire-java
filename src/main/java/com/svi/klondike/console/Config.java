package com.svi.klondike.console;

import com.svi.klondike.config.DrawMode;
import com.svi.klondike.config.GameConfig;
import com.svi.klondike.config.ShuffleType;

import java.util.Arrays;

public class Config {
    private static final int MIN_SHUFFLES = 1;
    private static final int MAX_SHUFFLES = 100;
    private static final int MIN_GAMES = 1;
    private static final int MAX_GAMES = 100;
    private static final int SINGLE_GAME = 1;
    private static final int DEFAULT_SHUFFLE_COUNT = 1;

    private final ConsoleInput input;

    public Config(ConsoleInput input){
        this.input = input;
    }

    public GameConfig ask() {
        ShuffleType shuffleType = input.chooseOne("Choose a shuffle:", Arrays.asList(ShuffleType.values()), ShuffleType::getLabel);

        DrawMode drawMode = input.chooseOne("Choose how many cards to draw:", Arrays.asList(DrawMode.values()), DrawMode::getLabel);

        int gameCount = input.readInt("\nHow many games should be played? (" + MIN_GAMES + "-" + MAX_GAMES + "): ", MIN_GAMES, MAX_GAMES);

        int shuffleCount = DEFAULT_SHUFFLE_COUNT;

        if (shuffleType.isFaro()) {
            if (gameCount == SINGLE_GAME) {
                shuffleCount = input.readInt("Number of shuffles (" + MIN_SHUFFLES + "-" + MAX_SHUFFLES + "): ", MIN_SHUFFLES, MAX_SHUFFLES);
            } else {
                System.out.println("Faro shuffles are deterministic, so game n uses n shuffles " + "to give different deals.");
            }
        }

        return new GameConfig(shuffleType, shuffleCount, drawMode, gameCount);
    }
}

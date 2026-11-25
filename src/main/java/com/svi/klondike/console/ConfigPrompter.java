package com.svi.klondike.console;

import com.svi.klondike.config.*;

import java.io.PrintStream;
import java.util.Arrays;

public class ConfigPrompter {
    private static final int MIN_SHUFFLES = 1;
    private static final int MAX_SHUFFLES = 100;
    private static final int MIN_GAMES = 1;
    private static final int MAX_GAMES = 100;
    private static final int SINGLE_GAME = 1;
    private static final int DEFAULT_SHUFFLE_COUNT = 1;
    private static final int FEWEST_GAMES_FOR_SEVERAL = 1;

    private final ConsoleInput input;
    private final PrintStream out;
    private final Settings settings;

    public ConfigPrompter(ConsoleInput input, PrintStream out, Settings settings) {
        this.input = input;
        this.out = out;
        this.settings = settings;
    }


    /**
     * Asks for shuffle, the draw mode and the number of games.
     */
    public GameConfig ask() {
        ShuffleType shuffleType = input.chooseOne(
                "Choose a shuffle:", Arrays.asList(ShuffleType.values()), ShuffleType::getLabel);

        PlayMode playMode = input.chooseOne(
                "How do you want to play?", Arrays.asList(PlayMode.values()), PlayMode::getLabel);

        DrawMode drawMode = input.chooseOne(
                "Choose how many cards to draw:", Arrays.asList(DrawMode.values()), DrawMode::getLabel);

        int gameCount = askGameCount(playMode);

        int shuffleCount = DEFAULT_SHUFFLE_COUNT;
        boolean singleGame = gameCount == 1;

        if (shuffleType.isFaro()) {
            if (singleGame) {
                shuffleCount = input.readInt(
                        "Number of shuffles (" + settings.minShuffles() + "-" + settings.maxShuffles() + "): ",
                        settings.minShuffles(), settings.maxShuffles());
            } else {
                out.println("Faro shuffles are deterministic, so game n uses n shuffles to give different deals.");
            }
        } else if (shuffleType == ShuffleType.NONE && !singleGame) {
            out.println("Without a shuffle every game is the same game, so the results cover one deal.");
        }

        return new GameConfig(shuffleType, shuffleCount, drawMode, gameCount);
    }

    /** One game for single mode; otherwise asks how many games, never fewer than two. */
    private int askGameCount(PlayMode playMode) {
        if (playMode == PlayMode.SINGLE_GAME) {
            return 1;
        }

        int fewest = Math.max(settings.minGames(), FEWEST_GAMES_FOR_SEVERAL);

        return input.readInt(
                "\nHow many games should be played? (" + fewest + "-" + settings.maxGames() + "): ",
                fewest, settings.maxGames());
    }
}

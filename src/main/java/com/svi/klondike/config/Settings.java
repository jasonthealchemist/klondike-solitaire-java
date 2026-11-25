package com.svi.klondike.config;

public record Settings(int minGames, int maxGames, int minShuffles, int maxShuffles,
                       int winRateCheckGames, int maxActionsPerGame, String deckFile) {
    private static final int MIN_GAMES = 1;
    private static final int MAX_GAMES = 100;
    private static final int MIN_SHUFFLES = 1;
    private static final int MAX_SHUFFLES = 100;
    private static final int WIN_RATE_CHECK_GAMES = 100;
    private static final int MAX_ACTIONS = 10_000;

    public Settings {
        requireAtLeastOne(minGames, "games.min");
        requireAtLeastOne(minShuffles, "shuffles.min");
        requireAtLeastOne(winRateCheckGames, "winrate.check.games");
        requireAtLeastOne(maxActionsPerGame, "engine.max.actions");

        if (maxGames < minGames) {
            throw new ConfigurationException("games.max must not be less than games.min");
        }

        if (maxShuffles < minShuffles) {
            throw new ConfigurationException("shuffles.max must not be less than shuffles.min");
        }

        deckFile = (deckFile == null) ? "" : deckFile.trim();
    }

    /**
     * The settings used when there is no settings file.
     */
    public static Settings defaults() {
        return new Settings(MIN_GAMES, MAX_GAMES, MIN_SHUFFLES, MAX_SHUFFLES,
                WIN_RATE_CHECK_GAMES, MAX_ACTIONS, "input.txt");
    }

    public boolean hasDeckFile() {
        return !deckFile.isEmpty();
    }

    private static void requireAtLeastOne(int value, String key) {
        if (value < 1) {
            throw new ConfigurationException(key + " must be at least 1 but is " + value);
        }
    }
}

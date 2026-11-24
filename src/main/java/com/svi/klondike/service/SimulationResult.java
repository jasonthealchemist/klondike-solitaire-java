package com.svi.klondike.service;

/**
 * The total of a set of games.
 */
public record SimulationResult(int games, int wins) {
    private static final double PERCENT = 100.0;

    public int losses() {
        return games - wins;
    }

    public double winRatePercent() {
        if (games == 0) {
            return 0;
        }

        return PERCENT * wins / games;
    }
}
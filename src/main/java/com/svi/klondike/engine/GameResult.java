package com.svi.klondike.engine;

public record GameResult(GameOutcome outcome, int actions) {

    public boolean isWon() {
        return outcome == GameOutcome.WON;
    }
}
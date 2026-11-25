package com.svi.klondike.service;

import com.svi.klondike.engine.GameAction;
import com.svi.klondike.engine.GameResult;
import com.svi.klondike.game.Board;

/**
 * Follows the games of a simulation as they are played. Every method does nothing by default, so a handler
 * only overrides what it needs.
 */
public interface GameHandler {
    GameHandler SILENT = new GameHandler() {
    };

    default void onGameStart(int gameNumber, Board board) {
        // Nothing by default.
    }

    default void onAction(Board board, int actionNumber, GameAction action) {
        // Nothing by default.
    }

    default void onGameFinished(int gameNumber, Board board, GameResult result, SimulationResult runningTotal) {
        // Nothing by default.
    }
}
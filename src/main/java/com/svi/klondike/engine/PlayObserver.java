package com.svi.klondike.engine;

import com.svi.klondike.game.Board;

/** Gets a callback after every action the engine takes. */
@FunctionalInterface
public interface PlayObserver {
    void onAction(Board board, int actionNumber, GameAction action);
}

package com.svi.klondike.service;

import com.svi.klondike.config.GameConfig;
import com.svi.klondike.engine.GameEngine;
import com.svi.klondike.engine.GameResult;
import com.svi.klondike.game.Board;
import com.svi.klondike.models.Card;
import com.svi.klondike.models.Deck;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Plays the chosen number of games one after another, tells a handler about them, and counts the wins. */
public class SimulationService {
    private final GameService gameService;
    private final GameEngine gameEngine;

    public SimulationService(GameService gameService, GameEngine gameEngine) {
        this.gameService = gameService;
        this.gameEngine = gameEngine;
    }

    /** Plays config.gameCount() games and returns the totals. */
    public SimulationResult run(GameConfig config, GameHandler handler) {
        int wins = 0;
        Set<List<Card>> deals = new HashSet<>();
        SimulationResult total = new SimulationResult(0, 0, 0);

        for (int game = 1; game <= config.gameCount(); game++) {
            Deck deck = gameService.createShuffledDeck(config.shuffleType(), config.shuffleCountFor(game));
            deals.add(deck.getOrder());

            Board board = gameService.deal(deck, config.drawMode());

            handler.onGameStart(game, board);
            GameResult result = gameEngine.play(board, handler::onAction);

            if (result.isWon()) {
                wins++;
            }

            total = new SimulationResult(game, wins, deals.size());
            handler.onGameFinished(game, board, result, total);
        }

        return total;
    }
}

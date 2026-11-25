package com.svi.klondike.service;

import com.svi.klondike.config.DrawMode;
import com.svi.klondike.config.ShuffleType;
import com.svi.klondike.config.Settings;
import com.svi.klondike.game.Board;
import com.svi.klondike.io.DeckLoader;
import com.svi.klondike.models.Deck;
import com.svi.klondike.shuffle.ShufflerFactory;

/**
 * Creates games: builds a deck, shuffles it as chosen, and deals it onto a board.
 */
public class GameService {
    private final ShufflerFactory shufflerFactory;
    private final DeckLoader deckLoader;
    private final Settings settings;

    public GameService(ShufflerFactory shufflerFactory, DeckLoader deckLoader, Settings settings) {
        this.shufflerFactory = shufflerFactory;
        this.deckLoader = deckLoader;
        this.settings = settings;
    }

    /**
     * Builds a deck (from the deck file if the settings name one, otherwise a standard deck) and shuffles it.
     *
     * @param shuffleCount how many times a Faro shuffle is repeated
     */
    public Deck createShuffledDeck(ShuffleType shuffleType, int shuffleCount) {
        Deck deck = settings.hasDeckFile() ? deckLoader.load(settings.deckFile()) : new Deck();
        deck.shuffleWith(shufflerFactory.create(shuffleType, shuffleCount));

        return deck;
    }

    /** Deals the deck onto a new board. */
    public Board deal(Deck deck, DrawMode drawMode) {
        Board board = new Board(drawMode.getCardCount());
        board.deal(deck);

        return board;
    }
}
package com.svi.klondike.game;

import com.svi.klondike.enums.Suits;
import com.svi.klondike.models.Card;
import com.svi.klondike.models.Deck;
import com.svi.klondike.piles.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private static final int TABLEAU_COUNT = 7;
    private static final int MIN_DRAW_COUNT = 1;

    private final int drawCount;
    private final List<TableauPile> tableau = new ArrayList<>();
    private final List<FoundationPile> foundations = new ArrayList<>();
    private final StockPile stock = new StockPile();
    private final Talon talon = new Talon();

    /**
     * @param drawCount how many cards one draw turns over
     * @throws IllegalArgumentException if drawCount is less than 1
     */
    public Board(int drawCount) {
        if (drawCount < MIN_DRAW_COUNT) {
            throw new IllegalArgumentException("Draw count must be at least " + MIN_DRAW_COUNT);
        }

        this.drawCount = drawCount;

        for (int index = 0; index < TABLEAU_COUNT; index++) {
            tableau.add(new TableauPile());
        }

        for (Suits suit : Suits.values()) {
            foundations.add(new FoundationPile(suit));
        }
    }

    /** Column n gets n cards (n = 1..7) with the top one face up. The rest of the deck becomes the stock. */
    public void deal(Deck deck) {
        for (int pileIndex = 0; pileIndex < TABLEAU_COUNT; pileIndex++) {
            TableauPile pile = tableau.get(pileIndex);
            int cardsInPile = pileIndex + 1;

            for (int count = 0; count < cardsInPile; count++) {
                pile.addCard(deck.dealTop());
            }

            pile.flipTopIfNeeded();
        }

        while (!deck.isEmpty()) {
            stock.addCard(deck.dealTop());
        }
    }

    /** Draws from the stock, or turns the talon over when the stock is empty. */
    public DrawResult drawFromStock() {
        if (stock.isEmpty()) {
            if (talon.isEmpty()) {
                return DrawResult.NOTHING_TO_DRAW;
            }

            stock.recycle(talon);
            return DrawResult.RECYCLED;
        }

        stock.draw(talon, drawCount);
        return DrawResult.DRAWN;
    }

    /**
     * The one move algorithm: ask the source, ask the destination, and only then move.
     * Nothing changes unless both agree.
     */
    public MoveResult move(Pile source, Pile destination, int count) {
        if (source == destination) {
            return MoveResult.SAME_PILE;
        }

        if (!source.canGive(count)) {
            return MoveResult.CANNOT_TAKE;
        }

        Card firstCard = source.peekFromTop(count);

        if (!destination.canAcceptRun(firstCard, count)) {
            return MoveResult.CANNOT_PLACE;
        }

        destination.addCards(source.removeTop(count));
        source.onCardsRemoved();

        return MoveResult.MOVED;
    }

    /** The foundation that accepts the card, or null if none does. */
    public FoundationPile findFoundationFor(Card card) {
        for (FoundationPile foundation : foundations) {
            if (foundation.canAccept(card)) {
                return foundation;
            }
        }

        return null;
    }

    /** The game is won when all four foundations are complete. */
    public boolean isWon() {
        for (FoundationPile foundation : foundations) {
            if (!foundation.isComplete()) {
                return false;
            }
        }

        return true;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public List<TableauPile> getTableau() {
        return Collections.unmodifiableList(tableau);
    }

    public List<FoundationPile> getFoundations() {
        return Collections.unmodifiableList(foundations);
    }

    public StockPile getStock() {
        return stock;
    }

    public Talon getTalon() {
        return talon;
    }
}
package com.svi.klondike.engine;

import com.svi.klondike.models.Card;
import com.svi.klondike.piles.Pile;

/**
 * One action taken by the engine: what happened, to which cards, and where they ended up. It never changes.
 * The destination pile and the highlight count tell a display which cards just moved.
 */
public class GameAction {
    public static final String STOCK = "stock";
    public static final String TALON = "talon";

    private final ActionType type;
    private final Card card;
    private final int cardCount;
    private final String from;
    private final String to;
    private final Pile destination;
    private final int highlightCount;

    private GameAction(ActionType type, Card card, int cardCount, String from, String to,
                       Pile destination, int highlightCount) {
        this.type = type;
        this.card = card;
        this.cardCount = cardCount;
        this.from = from;
        this.to = to;
        this.destination = destination;
        this.highlightCount = highlightCount;
    }

    /** card is the card now on top of the talon; drawnCount is how many cards were turned over. */
    public static GameAction draw(Card card, Pile talon, int drawnCount) {
        return new GameAction(ActionType.DRAW, card, drawnCount, STOCK, TALON, talon, drawnCount);
    }

    public static GameAction recycle(Pile stock) {
        return new GameAction(ActionType.RECYCLE, null, 0, TALON, STOCK, stock, 0);
    }

    public static GameAction toFoundation(Card card, String from, String to, Pile foundation) {
        return new GameAction(ActionType.TO_FOUNDATION, card, 1, from, to, foundation, 1);
    }

    /** card is the first (lowest) card of the run that was moved. */
    public static GameAction toColumn(Card card, int cardCount, String from, String to, Pile column) {
        return new GameAction(ActionType.TO_COLUMN, card, cardCount, from, to, column, cardCount);
    }

    public ActionType getType() {
        return type;
    }

    public Card getCard() {
        return card;
    }

    public int getCardCount() {
        return cardCount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    /** The pile the cards ended up on. */
    public Pile getDestination() {
        return destination;
    }

    /** How many cards, counted from the top of the destination, just arrived. */
    public int getHighlightCount() {
        return highlightCount;
    }
}

package com.svi.klondike.console;

import com.svi.klondike.game.Board;
import com.svi.klondike.engine.ActionType;
import com.svi.klondike.engine.GameAction;

import java.io.PrintStream;
import java.util.Locale;

/**
 * Shows a game one step at a time. Each card move or recycle is printed as one aligned line followed by the
 * board with the moved cards highlighted. Draws that follow each other are merged into one line.
 * It uses plain text only.
 */
public class MoveLogDisplay {
    private static final String SPACE = " ";
    private static final String ARROW = " -> ";
    private static final String SEPARATOR_LINE = "-";
    private static final int SEPARATOR_WIDTH = 56;
    private static final int NUMBER_WIDTH = 14;
    private static final int LABEL_WIDTH = 12;
    private static final int CARDS_WIDTH = 18;

    private final PrintStream out;
    private final BoardDisplay boardDisplay;

    private int firstDrawNumber;
    private int lastDrawNumber;
    private int pendingDraws;
    private String lastDrawnCard = "";

    public MoveLogDisplay(PrintStream out, BoardDisplay boardDisplay) {
        this.out = out;
        this.boardDisplay = boardDisplay;
    }

    /** Call before each game. */
    public void start() {
        pendingDraws = 0;
    }

    /** Shows one action. Draws are held back so that consecutive draws can be merged. */
    public void onAction(Board board, int actionNumber, GameAction action) {
        if (action.getType() == ActionType.DRAW) {
            if (pendingDraws == 0) {
                firstDrawNumber = actionNumber;
            }

            lastDrawNumber = actionNumber;
            pendingDraws++;

            // Remember the drawn card now: a later recycle turns it face down again.
            lastDrawnCard = action.getCard().toString();
            return;
        }

        printPendingDraws();
        printAction(actionNumber, action);
        boardDisplay.print(board, actionNumber, action);
    }

    /** Shows any draws still waiting to be merged. Call once when the game is over. */
    public void finish() {
        printPendingDraws();
    }

    private void printPendingDraws() {
        if (pendingDraws == 0) {
            return;
        }

        String numbers = (pendingDraws == 1)
                ? "Move " + firstDrawNumber
                : "Moves " + firstDrawNumber + "-" + lastDrawNumber;
        String label = (pendingDraws == 1) ? "DRAW" : "DRAW x" + pendingDraws;

        printSeparator();
        printLine(numbers, label, lastDrawnCard, GameAction.STOCK + ARROW + GameAction.TALON);

        pendingDraws = 0;
    }

    private void printAction(int actionNumber, GameAction action) {
        String numbers = "Move " + actionNumber;
        String route = action.getFrom() + ARROW + action.getTo();

        printSeparator();

        switch (action.getType()) {
            case TO_FOUNDATION:
                printLine(numbers, "FOUNDATION", cardsText(action), route);
                break;
            case TO_COLUMN:
                printLine(numbers, "MOVE", cardsText(action), route);
                break;
            default:
                printLine(numbers, "RECYCLE", "", route);
        }
    }

    private void printSeparator() {
        out.println(SEPARATOR_LINE.repeat(SEPARATOR_WIDTH));
    }

    private void printLine(String numbers, String label, String cards, String route) {
        out.println(pad(numbers, NUMBER_WIDTH) + pad(label, LABEL_WIDTH) + pad(cards, CARDS_WIDTH) + route);
    }

    /** The moved cards: the first card, and how many more came with it. */
    private String cardsText(GameAction action) {
        int extra = action.getCardCount() - 1;

        if (action.getType() != ActionType.TO_COLUMN || extra == 0) {
            return action.getCard().toString();
        }

        return action.getCard() + " +" + extra + (extra == 1 ? " card" : " cards");
    }

    private String pad(String text, int width) {
        return String.format(Locale.ROOT, "%-" + width + "s", text);
    }
}

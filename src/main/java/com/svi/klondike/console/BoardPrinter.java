package com.svi.klondike.console;

import com.svi.klondike.game.Board;
import com.svi.klondike.models.Card;
import com.svi.klondike.piles.FoundationPile;
import com.svi.klondike.piles.Pile;
import com.svi.klondike.piles.TableauPile;

import java.util.List;

public class BoardPrinter {
    private static final int CELL_WIDTH = 8;
    private static final String SPACE = " ";
    private static final String TITLE = "========== KLONDIKE SOLITAIRE ==========";
    private static final String EMPTY_SLOT = "[  ]";
    private static final String STOCK_BACK = "[??]";
    private static final String EMPTY_FOUNDATION = "--";

    public void print(Board board, int moveCount){
        System.out.println();
        System.out.println(TITLE);
        printBoard(board, moveCount, null);
    }

    public void print(Board board, int moveCount, GameAction lastAction) {
        System.out.println();
        printBoard(board, moveCount, lastAction);
    }

    private void printBoard(Board board, int moveCount, GameAction lastAction){
        printStockAndTalon(board, moveCount, lastAction);
        printFoundations(board, lastAction);
        System.out.println();
        printTableau(board, lastAction);
    }

    private void printStockAndTalon(Board board, int moveCount, GameAction lastAction) {
        Pile stock = board.getStock();

        String stockText = stock.isEmpty() ? EMPTY_SLOT : STOCK_BACK;

        System.out.println("Stock: " + stockText + " (" + ")"
                + "    Talon: " + talonText(board, lastAction)
                + "    Moves: " + moveCount);
    }

    private String talonText(Board board, GameAction lastAction) {
        Pile talon = board.getTalon();

        if (talon.isEmpty()) {
            return EMPTY_SLOT;
        }

        int visible = Math.min(board.getDrawCount(), talon.size());
        StringBuilder text = new StringBuilder();

        for (int fromTop = visible - 1; fromTop >= 0; fromTop--) {
            Card card = talon.peekFromTop(fromTop + 1);
            text.append(show(card, isHighlighted(lastAction, talon, fromTop))).append(SPACE);
        }

        return text.toString().trim();
    }

    private void printFoundations(Board board, GameAction lastAction){
        StringBuilder line = new StringBuilder("Foundations");

        for(FoundationPile foundation : board.getFoundations()){
            Card top = foundation.peekTop();
            String topText = (top == null) ? EMPTY_FOUNDATION : show(top, isHighlighted(lastAction, foundation, 0));

            line.append(SPACE).append(SPACE).append(foundation.getSuit().getDisplaySymbol()).append(SPACE).append(topText);
        }

        System.out.println(line);
    }

    private void printTableau(Board board, GameAction lastAction){
        List<TableauPile> tableau = board.getTableau();

        StringBuilder header = new StringBuilder();
        for(int index  = 0; index < tableau.size(); index++){
            header.append(pad(String.valueOf(index + 1)));
        }
        System.out.println(header);

        int tallest = 0;
        for (TableauPile pile : tableau){
            tallest = Math.max(tallest, pile.size());
        }

        for (int row = 0; row < tallest; row++) {
            StringBuilder line = new StringBuilder();

            for (TableauPile pile : tableau) {
                if (row < pile.size()) {
                    int fromTop = pile.size() - 1 - row;
                    line.append(cell(pile.getCards().get(row), isHighlighted(lastAction, pile, fromTop)));
                } else {
                    line.append(SPACE.repeat(CELL_WIDTH));
                }
            }

            System.out.println(line);
        }
    }

    private boolean isHighlighted(GameAction lastAction, Pile pile, int fromTop) {
        return lastAction != null
                && lastAction.getDestination() == pile
                && fromTop < lastAction.getHighlightCount();
    }

    private String show(Card card, boolean highlighted) {
        String text = card.toString();

        return text;
    }

    private String cell(Card card, boolean highlighted) {
        int padding = Math.max(0, CELL_WIDTH - card.toString().length());

        return show(card, highlighted) + SPACE.repeat(padding);
    }

    private String pad(String text) {
        return text + SPACE.repeat(Math.max(0, CELL_WIDTH - text.length()));
    }
}

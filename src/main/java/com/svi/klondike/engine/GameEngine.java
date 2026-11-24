package com.svi.klondike.engine;

import com.svi.klondike.game.Board;
import com.svi.klondike.game.DrawResult;
import com.svi.klondike.game.MoveResult;
import com.svi.klondike.models.Card;
import com.svi.klondike.piles.FoundationPile;
import com.svi.klondike.piles.Pile;
import com.svi.klondike.piles.TableauPile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Plays a game of Klondike by itself, using fixed rules. Each turn it makes the first useful move from this
 * list, and draws only if there is none:
 * <ol>
 *   <li>send a card to a foundation (the lowest top card first)</li>
 *   <li>move a face-up run to uncover a face-down card</li>
 *   <li>move a column with no hidden cards onto another one, emptying it</li>
 *   <li>play the top talon card onto a column</li>
 * </ol>
 * It only makes moves that cannot be undone, so it never goes back and forth. When a full pass through the
 * stock brings nothing new, no useful move is left and the game ends as stuck.
 */
public class GameEngine {
    private static final int SINGLE_CARD = 1;

    private final int maxActions;

    /** @param maxActions the engine gives up on a game after this many actions */
    public GameEngine(int maxActions) {
        this.maxActions = maxActions;
    }

    /** Plays the game on the board until it is won, stuck, or out of actions. */
    public GameResult play(Board board, PlayObserver observer) {
        int actions = 0;
        int drawsInARow = 0;

        while (!board.isWon() && actions < maxActions) {
            GameAction action = makeProgressMove(board);

            if (action != null) {
                drawsInARow = 0;
            } else {
                if (drawsInARow >= drawLimit(board)) {
                    break;
                }

                action = draw(board);

                if (action == null) {
                    break;
                }

                drawsInARow++;
            }

            actions++;
            observer.onAction(board, actions, action);
        }

        return new GameResult(board.isWon() ? GameOutcome.WON : GameOutcome.STUCK, actions);
    }

    /** Enough draws to go through the stock and talon completely, with every grouping of cards. */
    private int drawLimit(Board board) {
        int cyclingCards = board.getStock().size() + board.getTalon().size();

        return (cyclingCards + 1) * board.getDrawCount();
    }

    private GameAction draw(Board board) {
        int drawnCount = Math.min(board.getDrawCount(), board.getStock().size());
        DrawResult result = board.drawFromStock();

        return switch (result) {
            case DRAWN -> GameAction.draw(board.getTalon().peekTop(), board.getTalon(), drawnCount);
            case RECYCLED -> GameAction.recycle(board.getStock());
            default -> null;
        };
    }

    /** Returns the move made, or null if no useful move exists. */
    private GameAction makeProgressMove(Board board) {
        GameAction action = moveToAnyFoundation(board);

        if (action == null) {
            action = uncoverFaceDownCard(board);
        }

        if (action == null) {
            action = emptyColumn(board);
        }

        if (action == null) {
            action = playTalonCard(board);
        }

        return action;
    }

    /** Tries the piles in order of their top card, lowest first. Card is Comparable, so no extra code is needed. */
    private GameAction moveToAnyFoundation(Board board) {
        List<Pile> sources = new ArrayList<>();
        sources.add(board.getTalon());
        sources.addAll(board.getTableau());
        sources.removeIf(Pile::isEmpty);
        sources.sort(Comparator.comparing(Pile::peekTop));

        for (Pile source : sources) {
            GameAction action = moveToFoundation(board, source);

            if (action != null) {
                return action;
            }
        }

        return null;
    }

    private GameAction moveToFoundation(Board board, Pile source) {
        if (!source.canGive(SINGLE_CARD)) {
            return null;
        }

        FoundationPile target = board.findFoundationFor(source.peekTop());

        if (target == null) {
            return null;
        }

        return tryMove(board, source, target, SINGLE_CARD);
    }

    /** Moves a column's whole face-up run, so the face-down card beneath it can be turned over. */
    private GameAction uncoverFaceDownCard(Board board) {
        List<TableauPile> columns = new ArrayList<>(board.getTableau());
        columns.sort(Comparator.comparingInt(TableauPile::faceDownCount).reversed());

        for (TableauPile source : columns) {
            if (source.faceDownCount() == 0) {
                continue;
            }

            for (TableauPile destination : board.getTableau()) {
                GameAction action = tryMove(board, source, destination, source.faceUpCount());

                if (action != null) {
                    return action;
                }
            }
        }

        return null;
    }

    /** Moves a column that has no hidden cards onto another non-empty column, leaving an empty column. */
    private GameAction emptyColumn(Board board) {
        for (TableauPile source : board.getTableau()) {
            if (source.isEmpty() || source.faceDownCount() > 0) {
                continue;
            }

            for (TableauPile destination : board.getTableau()) {
                if (destination.isEmpty()) {
                    continue;
                }

                GameAction action = tryMove(board, source, destination, source.size());

                if (action != null) {
                    return action;
                }
            }
        }

        return null;
    }

    private GameAction playTalonCard(Board board) {
        for (TableauPile destination : board.getTableau()) {
            GameAction action = tryMove(board, board.getTalon(), destination, SINGLE_CARD);

            if (action != null) {
                return action;
            }
        }

        return null;
    }

    /** Makes the move if it is legal and returns it; returns null and changes nothing otherwise. */
    private GameAction tryMove(Board board, Pile source, Pile destination, int count) {
        if (!source.canGive(count)) {
            return null;
        }

        Card firstCard = source.peekFromTop(count);

        if (board.move(source, destination, count) != MoveResult.MOVED) {
            return null;
        }

        String from = nameOf(board, source);
        String to = nameOf(board, destination);

        if (board.getFoundations().contains(destination)) {
            return GameAction.toFoundation(firstCard, from, to, destination);
        }

        return GameAction.toColumn(firstCard, count, from, to, destination);
    }

    private String nameOf(Board board, Pile pile) {
        if (pile == board.getTalon()) {
            return GameAction.TALON;
        }

        int column = board.getTableau().indexOf(pile);

        if (column >= 0) {
            return "column " + (column + 1);
        }

        int foundation = board.getFoundations().indexOf(pile);

        return board.getFoundations().get(foundation).getSuit().getDisplaySymbol() + " foundation";
    }
}

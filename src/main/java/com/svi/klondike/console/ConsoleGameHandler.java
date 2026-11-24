package com.svi.klondike.console;

import com.svi.klondike.game.Board;
import com.svi.klondike.service.GameAction;
import com.svi.klondike.service.GameHandler;
import com.svi.klondike.service.GameResult;
import com.svi.klondike.service.SimulationResult;

import java.io.PrintStream;

/** Shows every game on the terminal: the starting board, each move with its board, and the final board. */
public class ConsoleGameHandler implements GameHandler {
    private static final int SINGLE_GAME = 1;
    private static final String BANNER_EDGE = "====================";

    private final int totalGames;
    private final PrintStream out;
    private final BoardDisplay boardDisplay;
    private final MoveLogDisplay moveLogDisplay;
    private final ResultDisplay resultDisplay;

    public ConsoleGameHandler(int totalGames, PrintStream out, BoardDisplay boardDisplay,
                              MoveLogDisplay moveLogDisplay, ResultDisplay resultDisplay) {
        this.totalGames = totalGames;
        this.out = out;
        this.boardDisplay = boardDisplay;
        this.moveLogDisplay = moveLogDisplay;
        this.resultDisplay = resultDisplay;
    }

    @Override
    public void onGameStart(int gameNumber, Board board) {
        if (totalGames > SINGLE_GAME) {
            out.println("\n" + BANNER_EDGE + " GAME " + gameNumber + " of " + totalGames + " " + BANNER_EDGE);
        }

        out.println("\nStarting position:");
        boardDisplay.print(board, 0);

        moveLogDisplay.start();
    }

    @Override
    public void onAction(Board board, int actionNumber, GameAction action) {
        moveLogDisplay.onAction(board, actionNumber, action);
    }

    @Override
    public void onGameFinished(int gameNumber, Board board, GameResult result, SimulationResult runningTotal) {
        moveLogDisplay.finish();

        out.println("\nFinal position:");
        boardDisplay.print(board, result.actions());

        resultDisplay.printGameResult(result, runningTotal, totalGames);
    }
}

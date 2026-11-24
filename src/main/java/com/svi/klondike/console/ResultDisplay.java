package com.svi.klondike.console;

import com.svi.klondike.config.GameConfig;
import com.svi.klondike.service.GameResult;
import com.svi.klondike.service.SimulationResult;

import java.io.PrintStream;
import java.util.Locale;

/** Shows how games ended, and the win rate. */
public class ResultDisplay {
    private final PrintStream out;

    public ResultDisplay(PrintStream out) {
        this.out = out;
    }

    /** Shows how a game ended. When several games are played, it also shows how many have been won so far. */
    public void printGameResult(GameResult result, SimulationResult runningTotal, int totalGames) {
        if (result.isWon()) {
            out.println("\n========== GAME WON ==========\nCompleted in " + result.actions() + " moves.");
        } else {
            out.println("\n========== NO MORE MOVES ==========\nThe game cannot be won from here ("
                    + result.actions() + " moves played).");
        }

        if (totalGames > 1) {
            out.println(String.format(Locale.ROOT, "Games won so far: %d of %d  (%.1f%%)",
                    runningTotal.wins(), runningTotal.games(), runningTotal.winRatePercent()));
        }
    }

    /** Shows the totals after several games. */
    public void printSummary(GameConfig config, SimulationResult result) {
        out.println();
        out.println("========== RESULTS ==========");
        out.println("Shuffle:        " + config.shuffleType().getLabel());
        out.println("Draw:           " + config.drawMode().getLabel());
        out.println("Games played:   " + result.games());
        out.println("Wins:           " + result.wins());
        out.println("Losses:         " + result.losses());
        out.println(String.format(Locale.ROOT, "Win rate:       %.1f%%", result.winRatePercent()));
        out.println("Distinct deals: " + result.distinctDeals());

        if (result.distinctDeals() < result.games()) {
            out.println("Some deals repeated, so the win rate covers only "
                    + result.distinctDeals() + " different games.");
        }
    }
}

package com.svi.klondike.console;

import com.svi.klondike.config.GameConfig;
import com.svi.klondike.config.Settings;
import com.svi.klondike.service.GameHandler;
import com.svi.klondike.service.SimulationResult;
import com.svi.klondike.service.SimulationService;

import java.io.PrintStream;

/**
 * Runs the chosen games and shows each one with its board. After a single game it shows a win rate check over
 * many silent games; after several games it shows their totals.
 */
public class GameRunner {
    private final SimulationService simulationService;
    private final PrintStream out;
    private final BoardDisplay boardDisplay;
    private final MoveLogDisplay moveLogDisplay;
    private final ResultDisplay resultDisplay;
    private final Settings settings;

    public GameRunner(SimulationService simulationService, PrintStream out, BoardDisplay boardDisplay,
                      MoveLogDisplay moveLogDisplay, ResultDisplay resultDisplay, Settings settings) {
        this.simulationService = simulationService;
        this.out = out;
        this.boardDisplay = boardDisplay;
        this.moveLogDisplay = moveLogDisplay;
        this.resultDisplay = resultDisplay;
        this.settings = settings;
    }

    /**
     * Plays and shows the games for the given choices.
     */
    public void run(GameConfig config) {
        GameHandler display = new ConsoleGameHandler(
                config.gameCount(), out, boardDisplay, moveLogDisplay, resultDisplay);

        SimulationResult result = simulationService.run(config, display);

        if (!config.isSingleGame()) {
            resultDisplay.printSummary(config, result);
        }
    }
}

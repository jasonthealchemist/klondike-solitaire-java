package com.svi.klondike.app;

import com.svi.klondike.config.Settings;
import com.svi.klondike.console.BoardDisplay;
import com.svi.klondike.console.ConfigPrompter;
import com.svi.klondike.console.ConsoleInput;
import com.svi.klondike.console.GameRunner;
import com.svi.klondike.console.GameSetup;
import com.svi.klondike.console.MoveLogDisplay;
import com.svi.klondike.console.ResultDisplay;
import com.svi.klondike.io.DeckLoader;
import com.svi.klondike.engine.GameEngine;
import com.svi.klondike.service.GameService;
import com.svi.klondike.service.SimulationService;
import com.svi.klondike.shuffle.ShufflerFactory;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

/**
 * Creates the objects and passes each one what it needs (constructor injection). It is the one place that
 * knows how the program is put together, much like the container in a Spring application.
 */
public class ApplicationContext {
    private final Settings settings;
    private final InputStream in;
    private final PrintStream out;

    public ApplicationContext(Settings settings, InputStream in, PrintStream out) {
        this.settings = settings;
        this.in = in;
        this.out = out;
    }

    /**
     * Builds the whole program and returns the object that runs it.
     */
    public GameSetup gameSetup() {
        ConsoleInput input = new ConsoleInput(new Scanner(in), out);

        GameService gameService = new GameService(new ShufflerFactory(new Random()), new DeckLoader(), settings);
        GameEngine gameEngine = new GameEngine(settings.maxActionsPerGame());

        BoardDisplay boardDisplay = new BoardDisplay(out);
        MoveLogDisplay moveLogDisplay = new MoveLogDisplay(out, boardDisplay);

        GameRunner gameRunner = new GameRunner(
                new SimulationService(gameService, gameEngine), out, boardDisplay, moveLogDisplay, new ResultDisplay(out), settings);

        return new GameSetup(input, new ConfigPrompter(input, out, settings), gameRunner, out);
    }
}
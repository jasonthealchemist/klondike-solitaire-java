package com.svi.klondike.console;

import java.io.PrintStream;

public class GameSetup {
    private static final String TITLE = "========== KLONDIKE SOLITAIRE ==========";

    private final ConsoleInput input;
    private final ConfigPrompter prompter;
    private final GameRunner gameRunner;
    private final PrintStream out;

    public GameSetup(ConsoleInput input, ConfigPrompter prompter, GameRunner gameRunner, PrintStream out) {
        this.input = input;
        this.prompter = prompter;
        this.gameRunner = gameRunner;
        this.out = out;
    }

    /** Runs the program until the user quits. Errors are not caught here: they reach the application. */
    public void start() {
        out.println(TITLE + "\n");

        if (!input.readEnterOrQuit("Press Enter to Start | Q to Quit: ")) {
            out.println("Exiting Klondike Solitaire.....");
            return;
        }

        do {
            gameRunner.run(prompter.ask());
        } while (input.readYesNo("\nWould you like to play again? (Y/N): "));

        out.println("Thank you for playing!");
    }
}
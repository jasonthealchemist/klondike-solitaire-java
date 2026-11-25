package com.svi.klondike.console;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Handles all keyboard input. Every answer is trimmed and checked in a loop until it is valid.
 */
public class ConsoleInput {
    private static final int FIRST_OPTION = 1;
    private static final String CHOICE_PROMPT = "> ";

    private final Scanner scanner;
    private final PrintStream out;

    public ConsoleInput(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public String readLine(String prompt) {
        out.println(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            String text = readLine(prompt);

            try {
                int value = Integer.parseInt(text);

                if (value >= min && value <= max) {
                    return value;
                }

                out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                out.println("Please enter a whole number.");
            }
        }
    }

    public boolean readYesNo(String prompt) {
        while (true) {
            String answer = readLine(prompt);

            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
                return true;
            }

            if (answer.equalsIgnoreCase("N") || answer.equalsIgnoreCase("NO")) {
                return false;
            }

            out.println("Please enter Y or N.");
        }
    }

    public boolean readEnterOrQuit(String prompt) {
        while (true) {
            String answer = readLine(prompt);

            if (answer.isEmpty()) {
                return true;
            }

            if (answer.equalsIgnoreCase("Q") || answer.equalsIgnoreCase("QUIT")) {
                if (readYesNo("Are you sure you want to quit the game? (Y/N): ")) {
                    return false;
                }
            } else {
                out.println("Invalid, please press Enter.");
            }
        }
    }

    /**
     * Shows a numbered list and returns the option the user picked.
     */
    public <T> T chooseOne(String title, List<T> options, Function<T, String> labelOf) {
        out.println("\n" + title);

        for (int index = 0; index < options.size(); index++) {
            out.println(" " + (index + FIRST_OPTION) + ". " + labelOf.apply(options.get(index)));
        }

        int choice = readInt(CHOICE_PROMPT, FIRST_OPTION, options.size());

        return options.get(choice - FIRST_OPTION);
    }
}
package com.svi.klondike.app;

import com.svi.klondike.config.ConfigurationException;
import com.svi.klondike.config.Settings;
import com.svi.klondike.io.DeckFormatException;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class KlondikeApp {
    private final Supplier<Settings> settingsSupplier;
    private final InputStream in;
    private final PrintStream out;
    private final PrintStream err;

    public KlondikeApp(Supplier<Settings> settingsSupplier, InputStream in, PrintStream out, PrintStream err) {
        this.settingsSupplier = settingsSupplier;
        this.in = in;
        this.out = out;
        this.err = err;
    }

    /**
     * Runs the program and returns the exit code. Each kind of error is handled separately.
     */
    public int run() {
        try {
            new ApplicationContext(settingsSupplier.get(), in, out).gameSetup().start();
            return ExitStatus.SUCCESS.getCode();
        } catch (NoSuchElementException e) {
            out.println("\nInput closed. Exiting Klondike Solitaire.....");
            return ExitStatus.SUCCESS.getCode();
        } catch (ConfigurationException e) {
            err.println("Configuration error: " + e.getMessage());
            return ExitStatus.CONFIGURATION_ERROR.getCode();
        } catch (DeckFormatException e) {
            err.println("Deck error: " + e.getMessage());
            return ExitStatus.DECK_ERROR.getCode();
        } catch (UncheckedIOException e) {
            err.println("File error: " + e.getMessage());
            return ExitStatus.FILE_ERROR.getCode();
        } catch (RuntimeException e) {
            err.println("Unexpected error: " + e);
            return ExitStatus.UNEXPECTED_ERROR.getCode();
        }
    }
}
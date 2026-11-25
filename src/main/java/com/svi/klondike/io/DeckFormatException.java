package com.svi.klondike.io;

/** Thrown when a deck file is missing or its content is not a valid deck. */
public class DeckFormatException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DeckFormatException(String message) {
        super(message);
    }
}

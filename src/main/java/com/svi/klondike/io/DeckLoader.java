package com.svi.klondike.io;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.enums.Suits;
import com.svi.klondike.models.Card;
import com.svi.klondike.models.Deck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Reads a deck from a text file. Cards are written as SUIT-RANK (for example D-A or H-10) and separated by
 * spaces, tabs, commas or new lines. The file needs 52 different cards. The first card in the file is the bottom
 * of the deck and the last card is the top.
 */
public class DeckLoader {
    private static final String TOKEN_DELIMITERS = " \t\r\n,";
    private static final String CARD_SEPARATOR = "-";
    private static final int CARD_PARTS = 2;
    private static final int SUIT_INDEX = 0;
    private static final int RANK_INDEX = 1;
    private static final String RESOURCE_PREFIX = "/";

    /**
     * Loads a deck from a file path, or if there is no such file, from the classpath.
     *
     * @throws DeckFormatException  if the file is missing or does not contain a valid deck
     * @throws UncheckedIOException if the file exists but cannot be read
     */
    public Deck load(String source) {
        try (Reader reader = open(source)) {
            return parse(reader);
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read the deck file: " + source, e);
        }
    }

    /**
     * Reads a deck from the given text.
     *
     * @throws DeckFormatException  if the text is not a valid deck
     * @throws UncheckedIOException if the text cannot be read
     */
    public Deck parse(Reader reader) {
        List<Card> cards = new ArrayList<>();
        Set<Card> seen = new HashSet<>();

        try (BufferedReader lines = new BufferedReader(reader)) {
            String line;

            while ((line = lines.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, TOKEN_DELIMITERS);

                while (tokenizer.hasMoreTokens()) {
                    Card card = parseCard(tokenizer.nextToken());

                    if (!seen.add(card)) {
                        throw new DeckFormatException("Duplicate card: " + card.getCode());
                    }

                    cards.add(card);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read the deck", e);
        }

        if (cards.size() != Deck.TOTAL_CARDS) {
            throw new DeckFormatException("The deck must contain exactly " + Deck.TOTAL_CARDS
                    + " cards but has " + cards.size());
        }

        return new Deck(cards);
    }

    private Card parseCard(String code) {
        String[] parts = code.split(CARD_SEPARATOR, -1);

        if (parts.length != CARD_PARTS) {
            throw new DeckFormatException("Invalid card format: " + code);
        }

        try {
            return new Card(Ranks.fromSymbol(parts[RANK_INDEX]), Suits.fromSymbol(parts[SUIT_INDEX]));
        } catch (IllegalArgumentException e) {
            throw new DeckFormatException("Invalid card: " + code);
        }
    }

    private Reader open(String source) throws IOException {
        try {
            Path path = Path.of(source);

            if (Files.isReadable(path)) {
                return Files.newBufferedReader(path, StandardCharsets.UTF_8);
            }
        } catch (InvalidPathException e) {
            // Not a usable file path: try the classpath below.
        }

        InputStream stream = DeckLoader.class.getResourceAsStream(RESOURCE_PREFIX + source);

        if (stream == null) {
            throw new DeckFormatException("Deck file not found: " + source);
        }

        return new InputStreamReader(stream, StandardCharsets.UTF_8);
    }
}

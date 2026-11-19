package com.svi.klondike.game;

import com.svi.klondike.enums.Suits;
import com.svi.klondike.models.Deck;
import com.svi.klondike.piles.FoundationPile;
import com.svi.klondike.piles.StockPile;
import com.svi.klondike.piles.TableauPile;
import com.svi.klondike.piles.Talon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private static final int TABLEAU_COUNT = 7;

    private final List<TableauPile> tableau = new ArrayList<>();
    private final List<FoundationPile> foundations = new ArrayList<>();
    private final StockPile stock = new StockPile();
    private final Talon talon = new Talon();

    private static final int DRAW_COUNT = 1;

    public Board() {
        for (int index = 0; index < TABLEAU_COUNT; index++) {
            tableau.add(new TableauPile());
        }

        for (Suits suit : Suits.values()) {
            foundations.add(new FoundationPile(suit));
        }
    }

    public void deal(Deck deck) {
        for (int pileIndex = 0; pileIndex < TABLEAU_COUNT; pileIndex++) {
            TableauPile pile = tableau.get(pileIndex);
            int cardsInPile = pileIndex + 1;

            for (int count = 0; count < cardsInPile; count++) {
                pile.addCard(deck.dealTop());
            }

            pile.flipTopIfNeeded();
        }

        while (!deck.isEmpty()) {
            stock.addCard(deck.dealTop());
        }
    }

    public List<TableauPile> getTableau() {
        return Collections.unmodifiableList(tableau);
    }

    public List<FoundationPile> getFoundations() {
        return Collections.unmodifiableList(foundations);
    }

    public StockPile getStock() {
        return stock;
    }

    public Talon getTalon() {
        return talon;
    }

    public DrawResult drawFromStock() {
        if (stock.isEmpty()) {
            if (talon.isEmpty()) {
                return DrawResult.NOTHING_TO_DRAW;
            }

            stock.recycle(talon);
            return DrawResult.RECYCLED;
        }

        stock.draw(talon, DRAW_COUNT);
        return DrawResult.DRAWN;
    }
}
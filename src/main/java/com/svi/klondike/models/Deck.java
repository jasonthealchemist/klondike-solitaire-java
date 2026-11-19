package com.svi.klondike.models;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.enums.Suits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    public static final int TOTAL_CARDS = Ranks.values().length * Suits.values().length;

    private final List<Card> cards = new ArrayList<>();

    public Deck(){
        for(Suits suit : Suits.values()){
            for (Ranks rank : Ranks.values()){
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle(){
        shuffle(new Random());
    }

    public void shuffle(Random random){
        Collections.shuffle(cards, random);
    }

    public Card dealTop(){
        return cards.remove(cards.size() - 1);
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public int size(){
        return cards.size();
    }
}

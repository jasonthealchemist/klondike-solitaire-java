package com.svi.klondike.piles;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.enums.Suits;
import com.svi.klondike.models.Card;

public class FoundationPile extends Pile {
    private final Suits suit;

    public FoundationPile(Suits suit){
        this.suit = suit;
    }

    public Suits getSuit(){
        return suit;
    }

    @Override
    public boolean canAccept(Card card){
        return card.getSuit() == suit && card.getRank().getValue() == size() + 1;
    }

    public boolean isComplete(){
        return size() == Ranks.values().length;
    }
}


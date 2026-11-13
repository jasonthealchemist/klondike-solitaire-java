package com.svi.klondike.piles;

import com.svi.klondike.models.Card;

public class StockPile extends Pile{
    @Override
    public boolean canAccept(Card card){
        return false;
    }


}

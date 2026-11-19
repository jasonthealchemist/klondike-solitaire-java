package com.svi.klondike.piles;

import com.svi.klondike.models.Card;

public class StockPile extends Pile{
    @Override
    public boolean canAccept(Card card){
        return false;
    }

    public int draw(Talon talon, int count){
        int drawn = 0;

        while(drawn < count && !isEmpty()){
            Card card = removeTop();
            card.turnFaceUp();
            talon.addCard(card);
            drawn++;
        }

        return drawn;
    }

    public void recycle(Talon talon){
        while(!talon.isEmpty()){
            Card card = talon.removeTop();
            card.turnFaceDown();
            addCard(card);
        }
    }
}

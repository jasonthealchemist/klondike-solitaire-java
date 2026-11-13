package com.svi.klondike.piles;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.models.Card;

public class TableauPile extends Pile{
    @Override
    public boolean canAccept(Card card){
        if(isEmpty()){
            return card.getRank() == Ranks.KING;
        }

        Card top = peekTop();

        return card.isOppositeColorTo(top) && card.isOneRankLowerThan(top);
    }

    @Override
    public boolean canGive(int count){
        return count >= SINGLE_CARD && count <= size() && peekFromTop(count).isFaceUp();
    }

    public void flipTopIfNeeded() {
        if (!isEmpty() && !peekTop().isFaceUp()) {
            peekTop().turnFaceUp();
        }
    }
}
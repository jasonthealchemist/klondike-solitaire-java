package com.svi.klondike.piles;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.models.Card;

public class TableauPile extends Pile {

    @Override
    public boolean canAccept(Card card) {
        if (isEmpty()) {
            return card.getRank() == Ranks.KING;
        }

        Card top = peekTop();

        return card.isOppositeColorTo(top) && card.isOneRankLowerThan(top);
    }

    /** Any length of run is fine: only its first card is checked, the rest is already a valid chain. */
    @Override
    public boolean canAcceptRun(Card firstCard, int count) {
        return canAccept(firstCard);
    }

    /**
     * A run can be taken if it fits in the pile and its lowest card is face up. Face-down cards only sit below
     * face-up ones, and face-up cards always form a valid chain, so checking one card is enough.
     */
    @Override
    public boolean canGive(int count) {
        return count >= SINGLE_CARD && count <= size() && peekFromTop(count).isFaceUp();
    }

    @Override
    public void onCardsRemoved() {
        flipTopIfNeeded();
    }

    /** Turns the top card face up if it is face down. */
    public void flipTopIfNeeded() {
        if (!isEmpty() && !peekTop().isFaceUp()) {
            peekTop().turnFaceUp();
        }
    }

    public int faceDownCount() {
        int count = 0;

        for (Card card : getCards()) {
            if (!card.isFaceUp()) {
                count++;
            }
        }

        return count;
    }

    public int faceUpCount() {
        return size() - faceDownCount();
    }
}

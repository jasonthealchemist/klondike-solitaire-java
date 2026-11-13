package com.svi.klondike;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.enums.Suits;
import com.svi.klondike.models.Card;
import com.svi.klondike.piles.*;

import java.util.List;

public class Main {

     static void main(String[] args) {
            Card aceHearts = new Card(Ranks.ACE, Suits.HEART);
            Card kingSpades = new Card(Ranks.KING, Suits.SPADE);

            aceHearts.turnFaceUp();
            kingSpades.turnFaceUp();
//
//            System.out.println(sixHearts);                                  // ??
//            sixHearts.turnFaceUp();
//            sevenSpades.turnFaceUp();
//            System.out.println(sixHearts + " / " + sevenSpades);            // H-6 ♥ / S-7 ♠
//            System.out.println(sixHearts.isOppositeColorTo(sevenSpades));   // true
//            System.out.println(sixHearts.isOneRankLowerThan(sevenSpades));  // true
//            System.out.println(sevenSpades.isOneRankLowerThan(sixHearts));  // false

            List<Pile> piles = List.of(new TableauPile(), new FoundationPile(Suits.HEART), new StockPile(), new WastePile());

            for (Pile p : piles) {
                   System.out.println(p.getClass().getSimpleName()
                           + ": A=" + p.canAccept(aceHearts) + " K=" + p.canAccept(kingSpades));
            }
}
}

package com.svi.klondike;

import com.svi.klondike.enums.Ranks;
import com.svi.klondike.enums.Suits;
import com.svi.klondike.models.Card;

public class Main {

     static void main(String[] args) {
            Card sixHearts = new Card(Ranks.SIX, Suits.HEART);
            Card sevenSpades = new Card(Ranks.SEVEN, Suits.SPADE);

            System.out.println(sixHearts);                                  // ??
            sixHearts.turnFaceUp();
            sevenSpades.turnFaceUp();
            System.out.println(sixHearts + " / " + sevenSpades);            // H-6 ♥ / S-7 ♠
            System.out.println(sixHearts.isOppositeColorTo(sevenSpades));   // true
            System.out.println(sixHearts.isOneRankLowerThan(sevenSpades));  // true
            System.out.println(sevenSpades.isOneRankLowerThan(sixHearts));  // false
}
}

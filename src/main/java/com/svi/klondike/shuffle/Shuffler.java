package com.svi.klondike.shuffle;

import com.svi.klondike.models.Card;

import java.util.List;

public interface Shuffler {
    void shuffle(List<Card> cards);
}

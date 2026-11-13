package com.svi.klondike.piles;

import com.svi.klondike.models.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Pile {
    protected static final int SINGLE_CARD = 1;

    private final List<Card> cards = new ArrayList<>();

    public abstract boolean canAccept(Card card);

    public boolean canGive(int count){
        return count == SINGLE_CARD && !isEmpty();
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public int size(){
        return cards.size();
    }

    public Card peekTop(){
        if(isEmpty()){
            return null;
        }
        return cards.get(cards.size() - 1);
    }

    public Card peekFromTop(int count){
        return cards.get(cards.size() - count);
    }

    public void addCards(List<Card> newCards){
        cards.addAll(newCards);
    }

    public Card removeTop(){
        return cards.remove(cards.size() -1);
    }

    public List<Card> removeTop(int count){
        int start = cards.size() - count;

        List<Card> removed = new ArrayList<>(cards.subList(start, cards.size()));
        cards.subList(start, cards.size()).clear();

        return removed;
    }

    public List<Card> getCards(){
        return Collections.unmodifiableList(cards);
    }
}

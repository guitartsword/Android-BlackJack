package com.example.guitart.blackjack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by guitart on 7/5/2016.
 */
public class Deck {
    private ArrayList<Card> deck = new ArrayList();
    private int position = 0;
    public Deck(boolean shuffle) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                deck.add(new Card(i, j));
            }
        }
        if (shuffle)
            shuffleDeck();
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }
    public Card takeCard() {
        if(position < deck.size()){
            return deck.get(position++);
        }
        position = 0;
        return deck.get(position++);
    }
}
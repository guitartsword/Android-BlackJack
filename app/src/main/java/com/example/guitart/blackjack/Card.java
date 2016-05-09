package com.example.guitart.blackjack;

/**
 * Created by guitart on 7/5/2016.
 */
public class Card {
    private int value;
    final public String[] values = {"ace","two","three","four","five","six","seven","eight","nine","ten","jack","queen","king"};
    private int type;
    final public String[] types = {"clubs","diamonds","hearts","spades","spades2"};

    public Card(int value, int type) {
        this.value = value;
        this.type = type;
    }
    public String getName(){
        String name = values[value] + "_of_";
        name += types[type];
        return name;
    }

    public int getValue() {
        return value+1;
    }
}

package com.Team5.demo.api.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void clear() {
        cards.clear();
    }

    public int getValue() {
        int value = 0;
        int numAces = 0;
        for (Card card : cards) {
            switch (card.getRank()) {
                case TWO: value += 2; break;
                case THREE: value += 3; break;
                case FOUR: value += 4; break;
                case FIVE: value += 5; break;
                case SIX: value += 6; break;
                case SEVEN: value += 7; break;
                case EIGHT: value += 8; break;
                case NINE: value += 9; break;
                case TEN: case JACK: case QUEEN: case KING:
                    value += 10; break;
                case ACE:
                    numAces++;
                    value += 11; // Assume Aces initially worth 11
                    break;
            }
        }
        // Adjust Ace value if needed
        while (value > 21 && numAces > 0) {
            value -= 10; // Change Ace value from 11 to 1
            numAces--;
        }
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card).append(" , ");
        }
        return sb.toString();
    }
}

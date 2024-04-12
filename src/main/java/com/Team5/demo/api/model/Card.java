package com.Team5.demo.api.model;

enum Suit { // Enum for card suits
    HEARTS,
    DIAMONDS,
    CLUBS,
    SPADES
}

enum Rank { // Enum for card ranks
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
}

// Class representing a playing card
public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}


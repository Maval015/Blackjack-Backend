package com.Team5.demo.service;

import com.Team5.demo.api.model.Deck;
import com.Team5.demo.api.model.Hand;
import com.Team5.demo.api.model.Player;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BlackjackService {
    private final Deck deck;
    private final Player player;
    private final Player dealer;

    public BlackjackService() {
        deck = new Deck();
        player = new Player();
        dealer = new Player();
    }

    public void dealInitialCards() {
        // Deal two cards to the player
        player.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());

        // Deal two cards to the dealer
        dealer.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());

        // Calculate and store the initial hand values
        int playerScore = getPlayerScore();
        int dealerScore = getDealerScore();

        // Print initial hands for debugging purposes
        System.out.println("Player Hand: " + player.getHand());
        System.out.println("Dealer Hand: " + dealer.getHand());
        System.out.println("Player Score: " + playerScore);
        System.out.println("Dealer Score: " + dealerScore);

    }

    public boolean isBust(int playerHand) {
        return player.getHand().getValue() > 21;
    }

    public boolean hasBlackjack(int playerHand) {
        return player.getHand().getValue() == 21;
    }

    public boolean isBustDealer(Player dealer) {
        return dealer.getHand().getValue() > 21;
    }

    public boolean hasBlackjackDealer(Player dealer) {
        return dealer.getHand().getValue() == 21;
    }

    public boolean shouldDealerHit(Player dealer) {
        // Dealer should hit if their hand value is less than 17
        return dealer.getHand().getValue() < 17;
    }

    public int getPlayerScore() {
        return player.getHand().getValue();
    }

    public int getDealerScore() {
        return dealer.getHand().getValue();
    }

    public Hand getPlayerHand() {
        return player.getHand();
    }

    public Hand getDealerHand() {
        return dealer.getHand();
    }



    // Additional methods for gameplay (hit, stand, etc.) would go here
}

package com.Team5.demo.service;

import com.Team5.demo.api.model.Deck;
import com.Team5.demo.api.model.Hand;
import com.Team5.demo.api.model.Player;
import com.Team5.demo.api.model.User;
import com.Team5.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BlackjackService {

    @Autowired
    private UserRepository userRepository;

    private boolean gameOngoing = false;
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

        gameOngoing = true;

        // Print initial hands for debugging purposes
        System.out.println("Player Hand: " + player.getHand());
        System.out.println("Dealer Hand: " + dealer.getHand());
        System.out.println("Player Score: " + playerScore);
        System.out.println("Dealer Score: " + dealerScore);

    }

    public void updateTokens(User user, int betAmount, int outcome) {
        int newTokens = user.getAvailableTokens();

        // Update tokens based on the outcome of the game

        if (outcome == 1) {
            newTokens += betAmount; // Player wins, add the bet amount to the user's tokens
        }
        else if (outcome == -1) {
            newTokens -= betAmount; // Dealer wins, deduct the bet amount from the user's tokens
        }

        // Update the user's tokens in the database
        user.setAvailableTokens(newTokens);
        userRepository.save(user);
    }

    public void playerHits() {
        player.getHand().addCard(deck.drawCard());
    }

    public void dealerHits() {
        dealer.getHand().addCard(deck.drawCard());
    }

    public int determineWinner() {
        if (DealerhasBlackjack(getDealerScore())) {
            return -1;
        }
        else if (isBust(getPlayerScore())) {
            return -1;
        }
        else if (isBust((getDealerScore()))) {
            return 1;
        }
        else if (hasBlackjack(getPlayerScore())){
            return 1;
        }

        else {
            if (getPlayerScore() > getDealerScore())
                return 1;
            else if (getPlayerScore() < getDealerScore())
                return -1;
            else
                return 0;
        }
    }

    public void resetGame() {
        player.getHand().clear();
        dealer.getHand().clear();

        gameOngoing = false;
    }

    public boolean isBust(int playerHand) {
        return player.getHand().getValue() > 21;
    }

    public boolean hasBlackjack(int playerHand) {
        return player.getHand().getValue() == 21;
    }

    public boolean DealerisBust(int playerHand) {
        return dealer.getHand().getValue() > 21;
    }

    public boolean DealerhasBlackjack(int playerHand) {
        return dealer.getHand().getValue() == 21;
    }

    public boolean shouldDealerHit(int playerHand) {
        return dealer.getHand().getValue() < 17; // Dealer should hit if their hand value is less than 17
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

    public boolean isGameOngoing() {
        return gameOngoing;
    }

    // Additional methods for gameplay (hit, stand, etc.) would go here
}

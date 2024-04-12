package com.Team5.demo.api.controller;

import com.Team5.demo.service.BlackjackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Team5.demo.api.model.Player;

@RestController
public class GameController {

    @Autowired
    private BlackjackService blackjackService;

    @PostMapping("/playGame")
    public String playGame() {
        // Call the dealInitialCards method in the BlackjackService
        blackjackService.dealInitialCards();

        int playerScore = blackjackService.getPlayerScore();
        int dealerScore = blackjackService.getDealerScore();

        // Check if either player or dealer has Blackjack
        boolean playerHasBlackjack = blackjackService.hasBlackjack(playerScore);
        boolean dealerHasBlackjack = blackjackService.DealerhasBlackjack(dealerScore);

        if (playerHasBlackjack && dealerHasBlackjack) {
            return "Both player and dealer have Blackjack! It's a tie.";
        }
        else if (playerHasBlackjack) {
            return "Player has Blackjack! Player wins.";
        }
        else if (dealerHasBlackjack) {
            return "Dealer has Blackjack! Dealer wins.";
        }
        else {
                return "Initial Cards dealt! \n " +
                        "Player Hand: " + blackjackService.getPlayerHand() + " = " + playerScore + "\n " +
                        "Dealer Hand: " + blackjackService.getDealerHand() + " = " + dealerScore;
            }
        }

    @PostMapping("/hit")
    public String hit() {
        // Call the hit method in the BlackjackService
        blackjackService.playerHits();

        int playerScore = blackjackService.getPlayerScore();
        int dealerScore = blackjackService.getDealerScore();

        // Check if player is bust after hitting
        boolean playerIsBust = blackjackService.isBust(blackjackService.getPlayerScore());
        boolean playerHasBlackjack = blackjackService.hasBlackjack(blackjackService.getPlayerScore());

        if (playerIsBust) {
            return "Player is bust! Dealer wins.";
        }
        else if (playerHasBlackjack) {
            return "Player has Blackjack! Player wins.";
        }
        else {
            return "Player Hits! \n Player Hand: " + blackjackService.getPlayerHand() + playerScore +
                    "\n Dealer Hand: " + blackjackService.getDealerHand() + dealerScore;
        }
    }

    @PostMapping("/stand")
    public String stand() {
        // Call the stand method in the BlackjackService

        int playerScore = blackjackService.getPlayerScore();
        int dealerScore = blackjackService.getDealerScore();

        // Dealer's turn
        while (blackjackService.shouldDealerHit(blackjackService.getDealerScore())) {
            blackjackService.dealerHits();
            dealerScore = blackjackService.getDealerScore();
            return "Dealer Hits! \n" + blackjackService.getDealerHand() + " = " + dealerScore;
        }

        // Check the outcome of the game
        int winner = blackjackService.determineWinner();
        if (winner == 1) {
            return "Player wins!";
        } else if (winner == -1) {
            return "Dealer wins!";
        } else {
            return "It's a tie!";
        }
    }
    }

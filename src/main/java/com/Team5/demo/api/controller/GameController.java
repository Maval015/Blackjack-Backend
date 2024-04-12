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

    @GetMapping("/playGame")
    public String playGame() {
        // Call the dealInitialCards method in the BlackjackService
        blackjackService.dealInitialCards();

        int playerScore = blackjackService.getPlayerScore();
        int dealerScore = blackjackService.getDealerScore();

        // Check if either player or dealer has Blackjack
        boolean playerHasBlackjack = blackjackService.hasBlackjack(blackjackService.getPlayerScore());
        boolean dealerHasBlackjack = blackjackService.hasBlackjack(blackjackService.getDealerScore());

        // Check if dealer is bust
        boolean dealerIsBust = blackjackService.isBust(blackjackService.getDealerScore());

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

    }

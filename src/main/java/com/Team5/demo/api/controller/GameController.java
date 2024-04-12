package com.Team5.demo.api.controller;

import com.Team5.demo.api.model.PlayGameRequest;
import com.Team5.demo.api.model.User;
import com.Team5.demo.service.BlackjackService;
import com.Team5.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Team5.demo.api.model.Player;

@RestController
public class GameController {

    @Autowired
    private BlackjackService blackjackService;
    @Autowired
    private UserService userService;

    @PostMapping("/playGame")
    public String playGame(@RequestBody PlayGameRequest request) {
        String userName = request.getUserName();
        int betAmount = request.getBetAmount();

        if (blackjackService.isGameOngoing()) {
            return "Game is already on going, finish the round!";
        }

        blackjackService.dealInitialCards(); // Call the dealInitialCards method in the BlackjackService

        int playerScore = blackjackService.getPlayerScore();
        int dealerScore = blackjackService.getDealerScore();

        // Check if either player or dealer has Blackjack
        boolean playerHasBlackjack = blackjackService.hasBlackjack(playerScore);
        boolean dealerHasBlackjack = blackjackService.DealerhasBlackjack(dealerScore);

        User user = userService.findByUsername(userName);

        if (user == null) {
            return "User not found!"; // Handle the case where the user is not found
        }


        if (playerHasBlackjack && dealerHasBlackjack) {
            blackjackService.resetGame();
            return "Both player and dealer have Blackjack! It's a tie.";
        }
        else if (playerHasBlackjack) {
            blackjackService.updateTokens(user, betAmount, 1);
            blackjackService.resetGame();
            return "Player has Blackjack! Player wins.";
        }
        else if (dealerHasBlackjack) {
            blackjackService.updateTokens(user, betAmount, -1);
            blackjackService.resetGame();
            return "Dealer has Blackjack! Dealer wins.";
        }
        else {
                return "Initial Cards dealt! \n " +
                        "Player Hand: " + blackjackService.getPlayerHand() + " = " + playerScore + "\n " +
                        "Dealer Hand: " + blackjackService.getDealerHand() + " = " + dealerScore;
            }
        }

    @PostMapping("/hit")
    public String hit(@RequestBody PlayGameRequest request) {
        String userName = request.getUserName();
        int betAmount = request.getBetAmount();

        // Check if the game is ongoing
        if (!blackjackService.isGameOngoing()) {
            return "Game is not currently in progress. Start a new game.";
        }

        blackjackService.playerHits(); // Call the hit method in the BlackjackService
        int playerScore = blackjackService.getPlayerScore();
        int dealerScore = blackjackService.getDealerScore();

        if (blackjackService.isBust(playerScore)) {
            User user = userService.findByUsername(userName); // Update tokens for player losing the game

            if (user != null) {
                blackjackService.updateTokens(user, betAmount, -1);
            }
            else {
                return "User not found!";
            }


            blackjackService.resetGame(); // Reset game state
            return "Player busts! Game over.";
        }

        else if (blackjackService.hasBlackjack(playerScore)) {
            User user = userService.findByUsername(userName); // Update tokens for player losing the game

            if (user != null) {
                blackjackService.updateTokens(user, betAmount, 1);
            }
            else {
                return "User not found!";
            }


            blackjackService.resetGame(); // Reset game state
            return "Player has Blackjack! Game over.";
        }

        return "Player Hits! \n Player Hand: " + blackjackService.getPlayerHand() + playerScore +
                "\n Dealer Hand: " + blackjackService.getDealerHand() + dealerScore;
    }

    @PostMapping("/stand")
    public String stand(@RequestBody PlayGameRequest request) {
        String userName = request.getUserName();
        int betAmount = request.getBetAmount();

        // Check if the game is ongoing
        if (!blackjackService.isGameOngoing()) {
            return "Game is not currently in progress. Start a new game.";
        }

        int dealerScore = blackjackService.getDealerScore();

        // Dealer's turn
        while (blackjackService.shouldDealerHit(dealerScore)) {
            blackjackService.dealerHits();
            dealerScore = blackjackService.getDealerScore();

            if (blackjackService.DealerisBust(dealerScore)) {
                break;
            }
            return "Dealer Hits! \n" + blackjackService.getDealerHand() + " = " + dealerScore;
        }

        int winner = blackjackService.determineWinner(); // Check the outcome of the game

        User user = userService.findByUsername(userName);

        if (user != null) {
            blackjackService.updateTokens(user, betAmount, winner);
        }
        else {
            return "User not found!";
        }

        blackjackService.resetGame();

        if (winner == 1) {
            return "Player wins!";
        } else if (winner == -1) {
            return "Dealer wins!";
        } else {
            return "It's a tie!";
        }
    }
}

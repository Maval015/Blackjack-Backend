package com.Team5.demo.api.controller;

import com.Team5.demo.api.model.GameResponse;
import com.Team5.demo.api.model.PlayGameRequest;
import com.Team5.demo.api.model.User;
import com.Team5.demo.service.BlackjackService;
import com.Team5.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
public class GameController {

    @Autowired
    private BlackjackService blackjackService;
    @Autowired
    private UserService userService;

    @PostMapping("/playGame")
    public GameResponse playGame(@RequestBody PlayGameRequest request) {
        String userName = request.getUserName();
        int betAmount = request.getBetAmount();
        GameResponse response;

        if (blackjackService.isGameOngoing()) {
            throw new IllegalStateException("Game is already running!"); //Check exception handling in GameControllerAdvice Class
        }

        blackjackService.dealInitialCards(); // Call the dealInitialCards method in the BlackjackService

        int playerScore = blackjackService.getPlayerScore();
        int dealerScore = blackjackService.getDealerScore();
        response = new GameResponse(blackjackService.getPlayerHand().getHandString(), playerScore, blackjackService.getDealerHand().getHandString(), dealerScore, "");

        // Check if either player or dealer has Blackjack
        boolean playerHasBlackjack = blackjackService.hasBlackjack(playerScore);
        boolean dealerHasBlackjack = blackjackService.DealerhasBlackjack(dealerScore);

        User user = userService.findByUsername(userName);

        if (user == null) {
            throw new NoSuchElementException("User not found!");
        }


        if (playerHasBlackjack && dealerHasBlackjack) {
            blackjackService.resetGame();
            response.setGameOutcome("TIE");
            return response;
        }
        else if (playerHasBlackjack) {
            blackjackService.updateTokens(user, betAmount, 1);
            blackjackService.resetGame();
            response.setGameOutcome("PLAYER_WON");
            return response;
        }
        else {
            return response;
        }
    }

    @PostMapping("/hit")
    public GameResponse hit(@RequestBody PlayGameRequest request) {
        String userName = request.getUserName();
        int betAmount = request.getBetAmount();
        GameResponse response;

        // Check if the game is ongoing
        if (!blackjackService.isGameOngoing()) {
            throw new IllegalStateException("Game is not currently in progress. Start a new game.");
        }

        blackjackService.playerHits(); // Call the hit method in the BlackjackService
        int playerScore = blackjackService.getPlayerScore();
        int dealerScore = blackjackService.getDealerScore();
        response = new GameResponse(blackjackService.getPlayerHand().getHandString(), playerScore, blackjackService.getDealerHand().getHandString(), dealerScore, "");

        if (blackjackService.isBust(playerScore)) {
            User user = userService.findByUsername(userName); // Update tokens for player losing the game

            if (user != null) {
                blackjackService.updateTokens(user, betAmount, -1);
            }
            else {
                    throw new NoSuchElementException("User not found!");
            }


            blackjackService.resetGame(); // Reset game state
            response.setGameOutcome("PLAYER_LOST");
            return response;
        }

        else if (blackjackService.hasBlackjack(playerScore)) {
            User user = userService.findByUsername(userName); // Update tokens for player losing the game

            if (user != null) {
                blackjackService.updateTokens(user, betAmount, 1);
            }
            else {
                throw new NoSuchElementException("User not found!");
            }


            blackjackService.resetGame();// Reset game state
            response.setGameOutcome("PLAYER_WON");
            return response;
        }

        return response;
    }

    @PostMapping("/stand")
    public GameResponse stand(@RequestBody PlayGameRequest request) {
        String userName = request.getUserName();
        int betAmount = request.getBetAmount();
        GameResponse response;

        // Check if the game is ongoing
        if (!blackjackService.isGameOngoing()) {
            throw new IllegalStateException("Game is not currently in progress. Start a new game.");
        }

        int dealerScore = blackjackService.getDealerScore();
        response = new GameResponse(blackjackService.getPlayerHand().getHandString(), blackjackService.getPlayerScore(), blackjackService.getDealerHand().getHandString(), dealerScore, "");

        // Dealer's turn
        while (blackjackService.shouldDealerHit(dealerScore)) {

            blackjackService.dealerHits();
            dealerScore = blackjackService.getDealerScore();
            response.setDealerHand(blackjackService.getDealerHand().getHandString());
            response.setDealerScore(dealerScore);

            if (blackjackService.DealerisBust(dealerScore)) {
                blackjackService.resetGame();
                response.setGameOutcome("PLAYER_WON");
                return response;
            }
            return response;
        }

        int winner = blackjackService.determineWinner(); // Check the outcome of the game
        User user = userService.findByUsername(userName);

        if (user != null) {
            blackjackService.updateTokens(user, betAmount, winner);
        }
        else {
            throw new NoSuchElementException("User not found!");
        }

        blackjackService.resetGame();

        if (winner == 1) {
            response.setGameOutcome("PLAYER_WON");
            return response;
        }
        else if (winner == -1) {
            response.setGameOutcome("PLAYER_WON");
            return response;
        }
        else {
            response.setGameOutcome("TIE");
            return response;
        }
    }
}

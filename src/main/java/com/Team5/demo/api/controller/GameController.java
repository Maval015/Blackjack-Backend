package com.Team5.demo.api.controller;

import com.Team5.demo.api.model.GameResponse;
import com.Team5.demo.api.model.PlayGameRequest;
import com.Team5.demo.api.model.User;
import com.Team5.demo.service.BlackjackService;
import com.Team5.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private BlackjackService blackjackService;
    @Autowired
    private UserService userService;

    @PostMapping("/playGame")
    public GameResponse playGame(@RequestBody PlayGameRequest request) {
        String username = request.getUsername();
        int betAmount = request.getBetAmount();
        GameResponse response;
        System.out.println(username);

        User user = userService.findByUsername(username);

        if (user == null) {
            throw new NoSuchElementException("User not found!");
        }

        if (betAmount <= 0 || betAmount > user.getAvailableTokens()) {
            throw new ArithmeticException("Invalid bet amount. Bet must be greater than 0, but not more than your available tokens.");
        }

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
        String username = request.getUsername();
        int betAmount = request.getBetAmount();
        GameResponse response;

        User user = userService.findByUsername(username);

        // Check if the game is ongoing
        if (!blackjackService.isGameOngoing()) {
            throw new IllegalStateException("Game is not currently in progress. Start a new game.");
        }

        if (user == null) {
            throw new NoSuchElementException("User not found!");
        }

        if (betAmount <= 0 || betAmount > user.getAvailableTokens()) {
            throw new ArithmeticException("Invalid bet amount. Bet must be greater than 0, but not more than your available tokens.");
        }

        blackjackService.playerHits(); // Call the hit method in the BlackjackService
        int playerScore = blackjackService.getPlayerScore();
        int dealerScore = blackjackService.getDealerScore();
        response = new GameResponse(blackjackService.getPlayerHand().getHandString(), playerScore, blackjackService.getDealerHand().getHandString(), dealerScore, "");

        // Update tokens for player losing the game
        if (blackjackService.isBust(playerScore)) {

            blackjackService.updateTokens(user, betAmount, -1);
            blackjackService.resetGame(); // Reset game state
            response.setGameOutcome("PLAYER_LOST");
            return response;
        }

        //Update tokens for player winning
        else if (blackjackService.hasBlackjack(playerScore)) {

            blackjackService.updateTokens(user, betAmount, 1);
            blackjackService.resetGame();// Reset game state
            response.setGameOutcome("PLAYER_WON");
            return response;
        }

        //Determines a tie.
        else if (blackjackService.hasBlackjack(playerScore) && blackjackService.DealerhasBlackjack(dealerScore)) {
            blackjackService.resetGame();
            response.setGameOutcome("TIE");
            return response;
        }

        return response;
    }

    @PostMapping("/stand")
    public List<GameResponse> stand(@RequestBody PlayGameRequest request) {
        String username = request.getUsername();
        int betAmount = request.getBetAmount();
        List<GameResponse> responses = new ArrayList<>();

        User user = userService.findByUsername(username);

        // Check if the game is ongoing
        if (!blackjackService.isGameOngoing()) {
            throw new IllegalStateException("Game is not currently in progress. Start a new game.");
        }

        if (user == null) {
            throw new NoSuchElementException("User not found!");
        }

        if (betAmount <= 0 || betAmount > user.getAvailableTokens()) {
            throw new ArithmeticException("Invalid bet amount. Bet must be greater than 0, but not more than your available tokens.");
        }

        int dealerScore = blackjackService.getDealerScore();
        GameResponse response = new GameResponse(blackjackService.getPlayerHand().getHandString(), blackjackService.getPlayerScore(), blackjackService.getDealerHand().getHandString(), dealerScore, "");

        // Dealer's turn
        while (blackjackService.shouldDealerHit(dealerScore)) {
            blackjackService.dealerHits();
            dealerScore = blackjackService.getDealerScore();
            response.setDealerHand(blackjackService.getDealerHand().getHandString());
            response.setDealerScore(dealerScore);
            responses.add(response);
            response = new GameResponse(blackjackService.getPlayerHand().getHandString(), blackjackService.getPlayerScore(), blackjackService.getDealerHand().getHandString(), dealerScore, "");
        }

        // Final response after dealer's turn
        if (blackjackService.DealerisBust(dealerScore) || dealerScore < blackjackService.getPlayerScore()) {
            blackjackService.resetGame();
            response.setGameOutcome("PLAYER_WON");
            responses.add(response);
        } else if (blackjackService.DealerhasBlackjack(dealerScore) || dealerScore > blackjackService.getPlayerScore()) {
            blackjackService.resetGame();
            response.setGameOutcome("PLAYER_LOST");
            responses.add(response);
        } else {
            blackjackService.resetGame();
            response.setGameOutcome("TIE");
            responses.add(response);
        }

        return responses;
    }
}

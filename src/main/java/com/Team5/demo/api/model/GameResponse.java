package com.Team5.demo.api.model;

public class GameResponse {
    private String playerHand;
    private int playerScore;
    private String dealerHand;
    private int dealerScore;
    private String gameOutcome;


    public GameResponse(String playerHand, int playerScore, String dealerHand, int dealerScore, String gameOutcome) {
        this.playerHand = playerHand;
        this.playerScore = playerScore;
        this.dealerHand = dealerHand;
        this.dealerScore = dealerScore;
        this.gameOutcome = gameOutcome;
    }

    public String getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(String playerHand) {
        this.playerHand = playerHand;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public String getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(String dealerHand) {
        this.dealerHand = dealerHand;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public void setDealerScore(int dealerScore) {
        this.dealerScore = dealerScore;
    }

    public String getGameOutcome() {
        return gameOutcome;
    }

    public void setGameOutcome(String gameOutcome) {
        this.gameOutcome = gameOutcome;
    }
}

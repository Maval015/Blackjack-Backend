package com.Team5.demo.api.model;

public class PlayGameRequest {

    private String username;
    private int betAmount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public PlayGameRequest(String username, int betAmount) {
        this.username = username;
        this.betAmount = betAmount;
    }
}

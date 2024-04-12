package com.Team5.demo.api.model;

public class PlayGameRequest {

    private String userName;
    private int betAmount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public PlayGameRequest(String userName, int betAmount) {
        this.userName = userName;
        this.betAmount = betAmount;
    }
}

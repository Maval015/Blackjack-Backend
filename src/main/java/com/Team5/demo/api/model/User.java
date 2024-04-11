package com.Team5.demo.api.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @Column(name = "Username")
    private String userName;
    @Column(name = "Password")
    private String passWrd;
    @Column(name = "Email")
    private String email;
    @Column(name = "Available_Tokens")
    private int availableTokens;

    public User() {

    }

    public User(String userName, String passWrd, String email, int availableTokens) {
        this.userName = userName;
        this.passWrd = passWrd;
        this.email = email;
        this.availableTokens = availableTokens;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWrd() {
        return passWrd;
    }

    public void setPassWrd(String passWrd) {
        this.passWrd = passWrd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAvailableTokens() {
        return availableTokens;
    }

    public void setAvailableTokens(int availableTokens) {
        this.availableTokens = availableTokens;
    }
}

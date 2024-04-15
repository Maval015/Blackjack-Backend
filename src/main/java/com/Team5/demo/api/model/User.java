package com.Team5.demo.api.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "Email")
    private String email;
    @Column(name = "Available_Tokens")
    private int availableTokens;

    public User() {

    }

    public User(String username, String password, String email, int availableTokens) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.availableTokens = availableTokens;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

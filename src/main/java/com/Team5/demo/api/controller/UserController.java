package com.Team5.demo.api.controller;

import com.Team5.demo.api.model.CreateUserRequest;
import com.Team5.demo.api.model.User;
import com.Team5.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();
        User user = userService.login(username, password);

        if (user == null) {
            throw new NoSuchElementException("Incorrect Username or Password");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Login Successful!");
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody CreateUserRequest request) {

        userService.createAccount(request.getUsername(), request.getPassword(), request.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body("Successfully Created Account!");
    }

    @PostMapping("/user-tokens")
    public ResponseEntity<Integer> fetchUserTokens(@RequestBody User loginUser) {
        String username = loginUser.getUsername();
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new NoSuchElementException("User not Found!");
        }

        int availableTokens = user.getAvailableTokens();
        return ResponseEntity.status(HttpStatus.OK).body(availableTokens);
    }

}

/**
 * Controllers in Spring Boot handle incoming HTTP requests, process them, and return appropriate HTTP responses.
 * They act as an interface between the client (frontend or another service) and the backend logic.
 *
 * What to Put Here:
 * Request mapping: Define endpoints and HTTP methods (GET, POST, PUT, DELETE, etc.).
 * Request parameters and body parsing: Extract data from incoming requests.
 * Input validation: Validate request data.
 * Response generation: Prepare and send HTTP responses.
 *
 * Example: In your Blackjack project, the UserController would handle requests related to user management,
 * such as registration, login, profile updates, etc. Endpoints like /register, /login, /profile, etc., would be defined here.
 */

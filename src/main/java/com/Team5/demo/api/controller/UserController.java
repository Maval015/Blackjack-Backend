package com.Team5.demo.api.controller;

import com.Team5.demo.api.model.CreateUserRequest;
import com.Team5.demo.api.model.User;
import com.Team5.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User loginUser) {
        String userName = loginUser.getUserName();
        String passWrd = loginUser.getPassWrd();
        User user = userService.login(userName, passWrd);

        if (user != null) {
            return "Login Succsesful";
        }

        else {
            return "Login Failed! Invalid Username or Password!";
        }
    }

    @PostMapping("/createAccount")
    public String createAccount(@RequestBody CreateUserRequest request) {

        userService.createAccount(request.getUserName(), request.getPassWrd(), request.getEmail());
        return "Account created succesfully!";
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

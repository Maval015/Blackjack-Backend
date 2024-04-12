package com.Team5.demo.service;

import com.Team5.demo.api.model.User;
import com.Team5.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(String userName, String password) {

        User user = userRepository.findByUserName(userName); // Retrieve the user from the database based on the username


        if (user != null && user.getPassWrd().equals(password)) { // Check if the user exists and if the provided password matches
            return user; // Return the user if login is successful
        }

        else {
            return null; // Return null if login fails
        }
    }

    public void createAccount(String userName, String passWrd, String email) {

        if (userRepository.findByUserName(userName) != null) {
            throw new IllegalArgumentException("Username Already exists");
        }

        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email is already linked to existing account!");
        }

        User newUser = new User(userName, passWrd, email, 0);
        userRepository.save(newUser);
    }

    public User findByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }
}


/**
 * Services contain the business logic of your application. They encapsulate reusable and complex operations
 * that are not specific to handling HTTP requests. Services are typically called by controllers to perform specific tasks.
 *
 * What to Put Here:
 * Business logic: Implement functionalities specific to your application's requirements.
 * Data manipulation: Perform operations on data, interact with the database, etc.
 * External integrations: Communicate with external services or APIs.
 * Transaction management: Control transaction boundaries if your application uses a database.
 *
 * Example: In your Blackjack project, the UserService would handle operations related to user management, such as registering users, retrieving user information,
 * updating user profiles, etc. Complex operations related to the game logic would be implemented in separate services like GameService.
 */
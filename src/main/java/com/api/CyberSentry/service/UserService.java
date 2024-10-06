package com.api.CyberSentry.service;

import com.api.CyberSentry.models.User;
import com.api.CyberSentry.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Saving a user in the database
    public User createUser(User user){
        try {
            return userRepository.save(user);
        }
        catch (Exception e){
            //Catch and log the error
            throw new RuntimeException("Failed to create new user " + e.getMessage());
        }
    }

    //Get all users from database
    public List<User> getAllUsers(){
        try {
            return userRepository.findAll();
        }
        //Catch and log the error
        catch (Exception e){
            throw new RuntimeException("Failed to get all users " + e.getMessage());
        }
    }

    //Get a single user from database
    public Optional<User> getUserByUsername(String username){
        try {
            return getAllUsers()
                    .stream()
                    .filter(User -> username.equals(User.getUsername()))
                    .findFirst();
        }
        catch (Exception e){
            throw new RuntimeException("User not found " + e.getMessage());
        }
    }

    //Update user details
    public Optional<User> updateUser(Long id, User updatedUser){
        try {
            Optional<User> existingUser = userRepository.findById(id);
            if (existingUser.isPresent()) {
                User existingUser1 = existingUser.get();
                existingUser1.setEmail(updatedUser.getEmail());
                existingUser1.setPassword(updatedUser.getPassword());
                User savedUser = userRepository.save(existingUser1);
                return Optional.of(savedUser);
            }
            else {
                return Optional.empty();
            }
        }
        catch (Exception e){
            throw new RuntimeException("Failed to update user " + e.getMessage());
        }
    }

    //Deleting a user
    public boolean deleteUser(Long id){
        try {
            userRepository.deleteById(id);
            return true; //If successful
        }
        catch (Exception e){
            throw new RuntimeException("Failed to delete " + e.getMessage());
        }
    }
}

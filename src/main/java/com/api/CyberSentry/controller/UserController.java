package com.api.CyberSentry.controller;

import com.api.CyberSentry.models.User;
import com.api.CyberSentry.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Create a new user
    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    //Retrieve all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //Retrieve single user by username
    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUserById(@PathVariable String username){
        Optional<User> userOptional = userService.getUserByUsername(username);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Update a user
    @PutMapping(path = "/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        Optional<User> updatedUserOptional = userService.updateUser(id, user);
        return updatedUserOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Delete a user
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        boolean deleteStatus = userService.deleteUser(id);
        if (deleteStatus) {
            return ResponseEntity.ok("User with id " + id + " has been deleted successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user with ID " + id);
        }
    }

}

package com.api.CyberSentry.service;

import com.api.CyberSentry.dto.UserDTO;
import com.api.CyberSentry.models.User;
import com.api.CyberSentry.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    //Get all users from database
    public List<UserDTO> getAllUsers(){
        try {
            return userRepository.findAll().stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all users " + e.getMessage());
        }
    }

    //Get user by id
    public UserDTO getUserById(Long id) {
            User user = userRepository.findById(id).orElse(null);
            return user != null ? modelMapper.map(user, UserDTO.class) : null;
    }

    //Add a new user
    public UserDTO createUser(UserDTO userDto) {
        logger.info("Creating new user: {}", userDto);

        if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (userDto.getUsername() == null || userDto.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    //Get a single user from database
    public User getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user " + username + e.getMessage());
        }
    }

    //Update user details
    public UserDTO updateUser(Long id, UserDTO userDto) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
        existingUser.setEmail(userDto.getEmail());
        existingUser.setUsername(userDto.getUsername());
        existingUser.setPassword(userDto.getPassword());

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    //Deleting a user
    public boolean deleteUser(Long id) {

        if (userRepository.existsById(id)) {

            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

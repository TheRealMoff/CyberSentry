package com.api.CyberSentry.service;

import com.api.CyberSentry.dto.UserDTO;
import com.api.CyberSentry.models.Role;
import com.api.CyberSentry.models.User;
import com.api.CyberSentry.repository.RoleRepository;
import com.api.CyberSentry.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.api.CyberSentry.enums.ApiUserRole.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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
    public UserDTO createUser(UserDTO userDto, Boolean isAdmin) {

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();

        if (isAdmin && userDto.getRoles() != null && !userDto.getRoles().isEmpty()){
            roles.addAll(userDto.getRoles());
        }
        else {
            Role userRole = roleRepository.findByRole(USER)
                    .orElseThrow(() -> new RuntimeException("Error role not found"));
            roles.add(userRole);
        }
        
        user.setRoles(roles);
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
        existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

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

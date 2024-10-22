package com.api.CyberSentry.service;

import com.api.CyberSentry.models.User;
import com.api.CyberSentry.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
           throw new  UsernameNotFoundException(("User with username: "
                    + username + " not found"));
        }
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(role -> role.getRole().getGrantedAuthorities().stream())
                .collect(Collectors.toSet());

        return new
                org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }
}


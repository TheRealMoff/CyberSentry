package com.api.CyberSentry.security;

import com.api.CyberSentry.models.User;
import com.api.CyberSentry.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new BadCredentialsException("User not found");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            Set<GrantedAuthority> authorities = user.getRoles().stream()
                    .flatMap(role -> role.getRole().getGrantedAuthorities().stream())
                    .collect(Collectors.toSet());

            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}

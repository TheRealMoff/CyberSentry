package com.api.CyberSentry.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth -> {
                    auth
                            .requestMatchers("/api/users/**").permitAll()// Allow access to user-related endpoints
                            .requestMatchers("/api/register").permitAll()
                            .requestMatchers("/api/v1/**").permitAll()
                            .anyRequest().authenticated(); // Disable CSRF for development
                }));

        return http.build();
    }
}

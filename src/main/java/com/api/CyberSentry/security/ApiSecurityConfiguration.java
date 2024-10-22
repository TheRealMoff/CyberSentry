package com.api.CyberSentry.security;

import com.api.CyberSentry.enums.ApiUserPermissions;
import com.api.CyberSentry.enums.ApiUserRole;
import com.api.CyberSentry.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.api.CyberSentry.enums.ApiUserPermissions.*;
import static com.api.CyberSentry.enums.ApiUserRole.*;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;

    public ApiSecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(20);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth -> {
                    auth
                            .requestMatchers("/api/v1/register").permitAll()
                            .requestMatchers("/api/v1/users/**").hasAnyRole(ADMIN.name(), USER.name())
                            .requestMatchers("/users/**").hasAnyAuthority(USER_READ.getPermission())
//
//                            .requestMatchers(HttpMethod.GET, "/api/v1/incidents/**").hasAnyRole(ADMIN.name(), USER.name())
//                            .requestMatchers(HttpMethod.POST, "/api/v1/incidents/**", "/api/v1/admin/users/register").hasAnyAuthority(INCIDENT_WRITE.getPermission(), USER_WRITE.getPermission())
//                            .requestMatchers(HttpMethod.PUT, "/api/v1/incidents/**", "/api/v1/users/**").hasAnyAuthority(INCIDENT_WRITE.getPermission(), USER_WRITE.getPermission())
//                            .requestMatchers(HttpMethod.DELETE, "/api/v1/incidents/**").hasAuthority(INCIDENT_WRITE.getPermission())
                            .anyRequest()
                            .authenticated();
                }))
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(daoAuthenticationProvider());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);

        return provider;
    }
}

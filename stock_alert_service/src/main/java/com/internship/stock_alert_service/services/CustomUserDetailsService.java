package com.internship.stock_alert_service.services;

import com.internship.stock_alert_service.repositories.UserRepository;
import com.internship.stock_alert_service.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        User user;

        if (isUuid(input)) {
            user = userRepository.findById(UUID.fromString(input))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + input));
        } else {
            user = userRepository.findByEmail(input)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + input));
        }

        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getId().toString())
                .password(user.getPasswordHash())
                .authorities(Collections.emptyList())
                .build();
    }

    private boolean isUuid(String str) {
        try {
            UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
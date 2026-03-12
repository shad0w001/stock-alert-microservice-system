package com.internship.stock_monitor_service.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SimpleUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String uuidString) {
        // since this has no access to the user db we just return an empty details with the uuid
        return User.withUsername(uuidString)
                .password("")
                .authorities(Collections.emptyList())
                .build();
    }
}

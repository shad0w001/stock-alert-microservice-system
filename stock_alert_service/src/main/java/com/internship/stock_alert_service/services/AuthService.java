package com.internship.stock_alert_service.services;

import com.internship.stock_alert_service.dtos.auth.LoginRequestDto;
import com.internship.stock_alert_service.dtos.auth.LoginResponseDto;
import com.internship.stock_alert_service.dtos.auth.RegisterRequestDto;
import com.internship.stock_alert_service.entities.User;
import com.internship.stock_alert_service.errors.AuthErrors;
import com.internship.stock_alert_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.Result;
import security.JwtTokenProvider;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public Result<LoginResponseDto> login(LoginRequestDto request) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword());
            authenticationManager.authenticate(authToken);

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new AuthenticationCredentialsNotFoundException(
                            "User not found after authentication: " + request.getEmail()));

            String jwt = jwtTokenProvider.generateToken(user.getId());

            return Result.success(new LoginResponseDto(jwt, "Bearer", user.getId()));
        } catch (AuthenticationCredentialsNotFoundException ex) {
            return Result.failure(AuthErrors.notFound(request.getEmail()));
        } catch (Exception ex) {
            return Result.failure(AuthErrors.authProblem(ex.getMessage()));
        }
    }

    @Transactional
    public Result<String> register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return Result.failure(AuthErrors.alreadyExists(request.getEmail()));
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        return Result.success("User registered successfully");
    }
}

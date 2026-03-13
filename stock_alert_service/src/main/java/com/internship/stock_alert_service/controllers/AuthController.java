package com.internship.stock_alert_service.controllers;

import com.internship.stock_alert_service.dtos.auth.LoginRequestDto;
import com.internship.stock_alert_service.dtos.auth.RegisterRequestDto;
import com.internship.stock_alert_service.errors.AuthErrors;
import com.internship.stock_alert_service.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @Valid @RequestBody LoginRequestDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            var error = AuthErrors.invalidRequest(bindingResult);
            return ResponseEntity.badRequest().header(error.code()).body(error);
        }

        var result = authService.login(dto);

        if (result.isFailure()) {
            return ResponseEntity.status(result.getError().status()).body(result.getError());
        }

        return ResponseEntity.ok(result.getValue());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegisterRequestDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            var error = AuthErrors.invalidRequest(bindingResult);
            return ResponseEntity.badRequest().header(error.code()).body(error);
        }

        var result = authService.register(dto);

        if (result.isFailure()) {
            return ResponseEntity.status(result.getError().status()).body(result.getError());
        }

        return ResponseEntity.ok(result.getValue());
    }
}

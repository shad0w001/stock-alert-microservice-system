package com.internship.stock_alert_service.errors;

import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import result.ApiError;

import javax.naming.Binding;
import java.util.stream.Collectors;

public final class AuthErrors {

    private AuthErrors() {}

    public static ApiError invalidRequest(BindingResult bindingResult){
        String errorMessage = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ApiError.problem(
                "Authentication.InvalidRequest",
                errorMessage
        );
    }

    public static ApiError notFound(String email) {
        return ApiError.notFound(
                "Authentication.UserNotFound",
                "User with the email = '" + email + "' was not found"
        );
    }

    public static ApiError alreadyExists(String email) {
        return ApiError.conflict(
                "Authentication.UserAlreadyExists",
                "User with the email = '" + email + "' already exists"
        );
    }

    public static ApiError authProblem(String message) {
        return ApiError.conflict(
                "Authentication.UnexpectedError",
                "There was a problem with the authentication request: " + message
        );
    }
}

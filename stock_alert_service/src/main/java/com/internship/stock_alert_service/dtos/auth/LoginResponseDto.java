package com.internship.stock_alert_service.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private String tokenType;
    private UUID userId;
}

package com.internship.stock_alert_service.controllers;

import com.internship.stock_alert_service.dtos.alert.AlertCreateDto;
import com.internship.stock_alert_service.dtos.alert.AlertViewDto;
import com.internship.stock_alert_service.services.AlertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import result.Result;

import java.util.UUID;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public ResponseEntity<Object> getMyAlerts(@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());

        var result = alertService.getAlertsForUser(userId);

        return ResponseEntity.ok(result.getValue());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAlertById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {

        UUID userId = UUID.fromString(userDetails.getUsername());
        var result = alertService.getAlertById(userId, id);

        if (result.isFailure()) {
            return ResponseEntity
                    .status(result.getError().status())
                    .body(result.getError());
        }

        return ResponseEntity.ok(result.getValue());
    }

    @PostMapping
    public ResponseEntity<Object> createAlert(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AlertCreateDto dto,
            BindingResult bindingResult,
            UriComponentsBuilder uriBuilder) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        UUID userId = UUID.fromString(userDetails.getUsername());
        Result<AlertViewDto> result = alertService.createAlert(userId, dto);

        if (result.isFailure()) {
            return ResponseEntity
                    .status(result.getError().status())
                    .body(result.getError());
        }

        var alert = result.getValue();
        var uri = uriBuilder.path("/alerts/{id}").buildAndExpand(alert.getId()).toUri();

        return ResponseEntity.created(uri).body(alert);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAlert(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {

        UUID userId = UUID.fromString(userDetails.getUsername());
        var result = alertService.deleteAlert(userId, id);

        if (result.isFailure()) {
            return ResponseEntity
                    .status(result.getError().status())
                    .body(result.getError());
        }

        return ResponseEntity.noContent().build();
    }
}

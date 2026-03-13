package com.internship.stock_alert_service.services;

import com.internship.stock_alert_service.dtos.alert.AlertCreateDto;
import com.internship.stock_alert_service.dtos.alert.AlertViewDto;
import com.internship.stock_alert_service.entities.Alert;
import com.internship.stock_alert_service.entities.User;
import com.internship.stock_alert_service.errors.AlertErrors;
import com.internship.stock_alert_service.errors.AuthErrors;
import com.internship.stock_alert_service.mappers.AlertMapper;
import com.internship.stock_alert_service.repositories.AlertRepository;
import com.internship.stock_alert_service.repositories.UserRepository;
import events.AlertCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.Result;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;
    private final AlertMapper alertMapper;

    // String is the key (symbol), Object is the payload (AlertCreatedEvent)
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Result<List<AlertViewDto>> getAlertsForUser(UUID userId) {
        var list = alertRepository.findByUserId(userId)
                .stream()
                .map(alertMapper::toViewDto)
                .toList();
        return Result.success(list);
    }

    public Result<AlertViewDto> getAlertById(UUID userId, Long alertId) {
        var alert = alertRepository.findById(alertId).orElse(null);

        if (alert == null) return Result.failure(AlertErrors.notFound(alertId));

        // Security: Ensure user owns the alert they are requesting
        if (!alert.getUser().getId().equals(userId)) {
            return Result.failure(AlertErrors.unauthorized());
        }

        return Result.success(alertMapper.toViewDto(alert));
    }

    public Result<AlertViewDto> createAlert(UUID userId, AlertCreateDto dto) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Result.failure(AuthErrors.notFound(userId.toString()));
        }

        boolean exists = alertRepository.existsByUserIdAndSymbolAndTargetPriceAndConditionTypeAndStatus(
                userId,
                dto.getSymbol(),
                dto.getTargetPrice(),
                dto.getConditionType(),
                enums.AlertStatus.PENDING
        );

        if (exists) {
            return Result.failure(AlertErrors.alreadyExists(dto.getSymbol()));
        }

        try {
            // 1. Persist to Alert Service Database
            Alert entity = alertMapper.createDtoToEntity(dto);
            entity.setUser(user);
            entity.setStatus(enums.AlertStatus.PENDING);

            Alert saved = alertRepository.save(entity);

            // 2. Prepare Kafka Event
            AlertCreatedEvent event = AlertCreatedEvent.builder()
                    .alertId(saved.getId())
                    .userId(userId)
                    .symbol(saved.getSymbol())
                    .targetPrice(saved.getTargetPrice())
                    .conditionType(saved.getConditionType())
                    .status(saved.getStatus())
                    .build();

            // 3. Send to Kafka (Topic name, Key, Payload)
            // Using symbol as key ensures all events for the same stock go to the same partition
            kafkaTemplate.send("stock-alerts-topic", event.getSymbol(), event);

            log.info("Alert created and broadcasted for symbol: {}", event.getSymbol());

            return Result.success(alertMapper.toViewDto(saved));

        } catch (Exception e) {
            log.error("Failed to create alert: {}", e.getMessage());
            return Result.failure(AlertErrors.creationProblem(e.getMessage()));
        }
    }

    public Result<Void> deleteAlert(UUID userId, Long alertId) {
        var existing = alertRepository.findById(alertId).orElse(null);

        if (existing == null) {
            return Result.failure(AlertErrors.notFound(alertId));
        }

        if (!existing.getUser().getId().equals(userId)) {
            return Result.failure(AlertErrors.unauthorized());
        }

        alertRepository.delete(existing);

        // Note: For a robust system, you'd send an AlertDeletedEvent here
        // so the Monitor service can remove it from its 'monitored_alerts' table.

        return Result.success(null);
    }
}

package com.internship.stock_monitor_service.services;

import topics.KafkaTopics;
import events.AlertTriggeredEvent;
import com.internship.stock_monitor_service.entities.MonitoredAlert;
import com.internship.stock_monitor_service.repositories.MonitoredAlertRepository;
import enums.AlertStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertTriggerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final MonitoredAlertRepository monitoredAlertRepository;

    @Transactional
    public void handleTrigger(MonitoredAlert alert, BigDecimal triggerPrice) {

        alert.setStatus(AlertStatus.TRIGGERED);
        monitoredAlertRepository.save(alert);

        AlertTriggeredEvent event = AlertTriggeredEvent.builder()
                .alertId(alert.getAlertId())
                .triggerPrice(triggerPrice)
                .triggeredAt(LocalDateTime.now())
                .build();

        kafkaTemplate.send(KafkaTopics.STOCK_ALERT_TRIGGERED, alert.getSymbol(), event);
    }
}
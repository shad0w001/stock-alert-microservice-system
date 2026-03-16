package com.internship.stock_alert_service.services;

import topics.KafkaTopics;
import events.AlertTriggeredEvent;
import com.internship.stock_alert_service.repositories.AlertRepository;
import enums.AlertStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlertEventConsumer {

    private final AlertRepository alertRepository;

    @Transactional
    @KafkaListener(
            topics = KafkaTopics.STOCK_ALERT_TRIGGERED,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleAlertTriggered(AlertTriggeredEvent event) {

        var alert = alertRepository.findById(event.getAlertId()).orElse(null);

        if (alert == null) {
            log.error("Received trigger for Alert ID {}, but it doesn't exist in Alert Service DB!",
                    event.getAlertId());
            return;
        }

        alert.setStatus(AlertStatus.TRIGGERED);
        alertRepository.save(alert);
    }
}
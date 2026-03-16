package com.internship.stock_monitor_service.services.monitored_alerts;

import topics.KafkaTopics;
import events.AlertCreatedEvent;
import events.AlertDeletedEvent;
import com.internship.stock_monitor_service.entities.MonitoredAlert;
import com.internship.stock_monitor_service.repositories.MonitoredAlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlertEventConsumer {

    private final MonitoredAlertRepository monitoredAlertRepository;

    @KafkaListener(
            topics = KafkaTopics.STOCK_ALERT_CREATIONS,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleAlertCreated(AlertCreatedEvent event) {

        if(event == null){
            return;
        }

        MonitoredAlert monitoredAlert = MonitoredAlert.builder()
                .alertId(event.getAlertId())
                .symbol(event.getSymbol())
                .targetPrice(event.getTargetPrice())
                .conditionType(event.getConditionType())
                .status(event.getStatus())
                .build();

        monitoredAlertRepository.save(monitoredAlert);
    }

    @KafkaListener(
            topics = KafkaTopics.STOCK_ALERT_DELETIONS,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleAlertDeleted(AlertDeletedEvent event) {

        if (monitoredAlertRepository.existsById(event.getAlertId())) {

            monitoredAlertRepository.deleteById(event.getAlertId());

        } else {
            log.warn("Attempted to delete Alert {}, but it was not found in watchlist.", event.getAlertId());
        }
    }
}
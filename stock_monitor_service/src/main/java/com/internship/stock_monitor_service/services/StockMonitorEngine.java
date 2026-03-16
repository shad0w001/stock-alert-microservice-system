package com.internship.stock_monitor_service.services;

import com.internship.stock_monitor_service.dtos.stocks.StockViewDto;
import com.internship.stock_monitor_service.entities.MonitoredAlert;
import com.internship.stock_monitor_service.repositories.MonitoredAlertRepository;
import enums.AlertStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import result.Result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockMonitorEngine {

    private final MonitoredAlertRepository monitoredAlertRepository;
    private final StockService stockService;
    private final AlertTriggerService alertTriggerService;

    // Cron: Runs every minute, Monday through Friday
    // "0 * * * * MON-FRI"
    @Scheduled(cron = "${app.monitor.cron:0 * * * * *}")
    public void checkAlerts() {
        log.info("Monitoring engine heartbeat: Checking active alerts...");

        List<MonitoredAlert> activeAlerts = monitoredAlertRepository.findByStatus(AlertStatus.PENDING);

        if (activeAlerts.isEmpty()) {
            return;
        }

        var uniqueSymbols = activeAlerts.stream()
                .map(MonitoredAlert::getSymbol)
                .distinct()
                .toList();

        for (String symbol : uniqueSymbols) {
            Result<StockViewDto> response = stockService.getStockQuote(symbol);

            if (response.isSuccess()) {
                BigDecimal currentPrice = response.getValue().getCurrentPrice();

                activeAlerts.stream()
                        .filter(a -> a.getSymbol().equalsIgnoreCase(symbol))
                        .forEach(alert -> processPriceCheck(alert, currentPrice));
            } else {
                log.warn("Could not fetch price for {}: {}", symbol, response.getError().message());
            }
        }
    }

    private void processPriceCheck(MonitoredAlert alert, java.math.BigDecimal currentPrice) {
        boolean isTriggered = switch (alert.getConditionType()) {
            case ABOVE -> currentPrice.compareTo(alert.getTargetPrice()) >= 0;
            case BELOW -> currentPrice.compareTo(alert.getTargetPrice()) <= 0;
        };

        if (isTriggered) {
            log.info("ALERT TRIGGERED: {} reached {} (Target:{} {})",
                    alert.getSymbol(), currentPrice, alert.getStatus(), alert.getTargetPrice());
            alertTriggerService.handleTrigger(alert, currentPrice);
        }
    }
}
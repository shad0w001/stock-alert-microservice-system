package com.internship.stock_alert_service.repositories;

import com.internship.stock_alert_service.entities.Alert;
import enums.AlertCondition;
import enums.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findBySymbolAndStatus(String symbol, AlertStatus status);

    boolean existsByUserIdAndSymbolAndTargetPriceAndConditionTypeAndStatus(
            UUID userId,
            String symbol,
            BigDecimal targetPrice,
            AlertCondition conditionType,
            AlertStatus status
    );

    List<Alert> findByUserId(UUID userId);
}

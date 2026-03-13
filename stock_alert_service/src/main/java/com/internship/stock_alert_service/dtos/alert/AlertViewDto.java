package com.internship.stock_alert_service.dtos.alert;

import enums.AlertCondition;
import enums.AlertStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AlertViewDto {
    private Long id;
    private String symbol;
    private BigDecimal targetPrice;
    private AlertCondition conditionType;
    private AlertStatus status;
    private LocalDateTime createdAt;
}

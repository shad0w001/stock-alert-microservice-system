package com.internship.stock_monitor_service.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceHistoryViewDto {
    private BigDecimal price;
    private LocalDateTime recordedAt;
}

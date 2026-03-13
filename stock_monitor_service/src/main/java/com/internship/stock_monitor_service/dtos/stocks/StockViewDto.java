package com.internship.stock_monitor_service.dtos.stocks;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class StockViewDto {
    private String symbol;
    private BigDecimal currentPrice;
    private BigDecimal priceChange;
    private BigDecimal percentChange;
    private LocalDateTime lastRefreshed;
}

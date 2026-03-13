package com.internship.stock_monitor_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_quotes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockQuote {
    @Id
    private String symbol; // Just a String ID now

    @Column(name = "current_price", precision = 19, scale = 4)
    private BigDecimal currentPrice;
    @Column(name = "price_change")
    private BigDecimal priceChange;

    @Column(name = "percent_change")
    private BigDecimal percentChange;

    @Column(name = "high_price_day")
    private BigDecimal highPriceDay;

    @Column(name = "low_price_day")
    private BigDecimal lowPriceDay;

    @Column(name = "open_price_day")
    private BigDecimal openPriceDay;

    @Column(name = "prev_close_price")
    private BigDecimal prevClosePrice;

    @Column(name = "last_refreshed")
    private LocalDateTime lastRefreshed;
}

package com.internship.stock_monitor_service.dtos.stocks;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinnhubStockQuoteResponse {
    @JsonProperty("c") private BigDecimal currentPrice;
    @JsonProperty("d") private BigDecimal change;
    @JsonProperty("dp") private BigDecimal percentChange;
    @JsonProperty("h") private BigDecimal highDay;
    @JsonProperty("l") private BigDecimal lowDay;
    @JsonProperty("o") private BigDecimal openDay;
    @JsonProperty("pc") private BigDecimal previousClose;
    @JsonProperty("t") private Long timestamp;
}

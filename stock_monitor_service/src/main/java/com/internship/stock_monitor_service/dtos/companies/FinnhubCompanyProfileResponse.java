package com.internship.stock_monitor_service.dtos.companies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinnhubCompanyProfileResponse {

    private String country;

    private String currency;

    private String exchange;

    private String ipo;

    @JsonProperty("marketCapitalization")
    private Long marketCapitalization;

    private String name;

    private String phone;

    @JsonProperty("shareOutstanding")
    private BigDecimal shareOutstanding;

    @JsonProperty("ticker")
    private String symbol;

    @JsonProperty("weburl")
    private String webUrl;

    private String logo;

    @JsonProperty("finnhubIndustry")
    private String industry;
}

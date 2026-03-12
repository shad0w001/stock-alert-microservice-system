package com.internship.stock_monitor_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    @Id
    private String symbol;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    private String country;
    private String industry;

    @Column(name = "market_capitalization")
    private Long marketCapitalization;

    @Column(name = "shares_outstanding")
    private BigDecimal sharesOutstanding;

    private String website;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}

package com.internship.stock_monitor_service.entities;

import enums.AlertCondition;
import enums.AlertStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "monitored_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitoredAlert {

    @Id
    @Column(name = "alert_id")
    private Long alertId;

    @Column(nullable = false)
    private String symbol;

    @Column(name = "target_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal targetPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type", nullable = false)
    private AlertCondition conditionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertStatus status;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastSyncedAt = LocalDateTime.now();
    }
}

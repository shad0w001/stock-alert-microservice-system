package com.internship.stock_monitor_service.repositories;

import com.internship.stock_monitor_service.entities.MonitoredAlert;
import enums.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoredAlertRepository extends JpaRepository<MonitoredAlert, Long> {

    List<MonitoredAlert> findBySymbolAndStatus(String symbol, AlertStatus status);
}

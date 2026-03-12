package com.internship.stock_monitor_service.repositories;

import com.internship.stock_monitor_service.entities.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    List<PriceHistory> findBySymbolOrderByRecordedAtDesc(String symbol);
}

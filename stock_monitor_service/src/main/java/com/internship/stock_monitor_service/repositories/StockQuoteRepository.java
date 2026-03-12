package com.internship.stock_monitor_service.repositories;

import com.internship.stock_monitor_service.entities.StockQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockQuoteRepository extends JpaRepository<StockQuote, String> {

}

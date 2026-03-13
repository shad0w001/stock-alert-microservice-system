package com.internship.stock_monitor_service.controllers;

import com.internship.stock_monitor_service.services.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/{symbol}/quote")
    public ResponseEntity<Object> getStockQuote(@PathVariable String symbol) {
        var result = stockService.getStockQuote(symbol);

        if (result.isFailure()) {
            return ResponseEntity
                    .status(result.getError().status())
                    .body(result.getError());
        }

        return ResponseEntity.ok(result.getValue());
    }
}

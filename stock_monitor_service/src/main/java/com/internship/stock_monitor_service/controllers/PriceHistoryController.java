package com.internship.stock_monitor_service.controllers;

import com.internship.stock_monitor_service.services.PriceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    @GetMapping("/{symbol}")
    public ResponseEntity<Object> getStockHistory(@PathVariable String symbol) {
        var result = priceHistoryService.getHistoryForSymbol(symbol);

        if (result.isFailure()) {
            return ResponseEntity
                    .status(result.getError().status())
                    .body(result.getError());
        }

        return ResponseEntity.ok(result.getValue());
    }
}

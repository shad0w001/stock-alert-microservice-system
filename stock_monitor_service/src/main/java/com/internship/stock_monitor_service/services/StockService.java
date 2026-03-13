package com.internship.stock_monitor_service.services;

import com.internship.stock_monitor_service.clients.finnhub.FinnhubClient;
import com.internship.stock_monitor_service.dtos.stocks.FinnhubStockQuoteResponse;
import com.internship.stock_monitor_service.dtos.stocks.StockViewDto;
import com.internship.stock_monitor_service.entities.PriceHistory;
import com.internship.stock_monitor_service.entities.StockQuote;
import com.internship.stock_monitor_service.errors.StockErrors;
import com.internship.stock_monitor_service.mappers.StockMapper;
import com.internship.stock_monitor_service.repositories.PriceHistoryRepository;
import com.internship.stock_monitor_service.repositories.StockQuoteRepository;
import org.springframework.cache.annotation.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.Result;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockQuoteRepository stockQuoteRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final StockMapper stockMapper;
    private final StockQuoteCacheFetcher stockFetcher;

    @Transactional
    public Result<StockViewDto> getStockQuote(String symbol) {
        String normalizedSymbol = symbol.toUpperCase();

        try {
            // since this class and method are cached, this will hit said cache first
            FinnhubStockQuoteResponse response = stockFetcher.fetchFinnhubQuote(normalizedSymbol);

            if (response == null || response.getCurrentPrice() == null) {
                return Result.failure(StockErrors.notFound(normalizedSymbol));
            }

            var entity = stockMapper.toEntity(response, normalizedSymbol);

            return Result.success(stockMapper.toViewDto(entity));

        } catch (Exception e) {
            log.error("Error processing stock quote for {}: {}", normalizedSymbol, e.getMessage());
            return Result.failure(StockErrors.apiError(normalizedSymbol, e.getMessage()));
        }
    }
}

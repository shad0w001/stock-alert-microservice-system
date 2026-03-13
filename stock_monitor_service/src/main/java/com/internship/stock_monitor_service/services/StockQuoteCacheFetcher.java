package com.internship.stock_monitor_service.services;

import com.internship.stock_monitor_service.clients.finnhub.FinnhubClient;
import com.internship.stock_monitor_service.dtos.stocks.FinnhubStockQuoteResponse;
import com.internship.stock_monitor_service.entities.PriceHistory;
import com.internship.stock_monitor_service.entities.StockQuote;
import com.internship.stock_monitor_service.mappers.StockMapper;
import com.internship.stock_monitor_service.repositories.PriceHistoryRepository;
import com.internship.stock_monitor_service.repositories.StockQuoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockQuoteCacheFetcher {

    private final FinnhubClient finnhubClient;
    private final StockQuoteRepository stockQuoteRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final StockMapper stockMapper;

    @Cacheable(
            value = "stock_quotes",
            key = "#symbol",
            unless = "#result == null || #result.currentPrice == null"
    )
    @Transactional
    public FinnhubStockQuoteResponse fetchFinnhubQuote(String symbol) {
        log.info("Symbol {} not in cache or expired. Fetching live and updating DB...", symbol);

        FinnhubStockQuoteResponse response = finnhubClient.getQuote(symbol);

        if (response != null && response.getCurrentPrice() != null) {
            StockQuote entity = stockMapper.toEntity(response, symbol);
            stockQuoteRepository.save(entity);

            PriceHistory history = PriceHistory.builder()
                    .symbol(symbol)
                    .price(response.getCurrentPrice())
                    .recordedAt(LocalDateTime.now())
                    .build();
            priceHistoryRepository.save(history);
        }

        return response;
    }
}

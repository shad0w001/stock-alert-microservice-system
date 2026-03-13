package com.internship.stock_monitor_service.services;

import com.internship.stock_monitor_service.clients.finnhub.FinnhubClient;
import com.internship.stock_monitor_service.dtos.stocks.FinnhubStockQuoteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockQuoteCacheFetcher {

    private final FinnhubClient finnhubClient;

    @Cacheable(
            value = "stock_quotes",
            key = "#symbol",
            unless = "#result == null || #result.currentPrice == null"
    )
    public FinnhubStockQuoteResponse fetchFinnhubQuote(String symbol) {
        log.info("Symbol {} not in cache. Fetching live from Finnhub API...", symbol);
        return finnhubClient.getQuote(symbol);
    }
}

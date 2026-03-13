package com.internship.stock_monitor_service.clients.finnhub;

import com.internship.stock_monitor_service.dtos.companies.FinnhubCompanyProfileResponse;
import com.internship.stock_monitor_service.dtos.stocks.FinnhubStockQuoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class FinnhubClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${finnhub.api.base-url}")
    private String baseUrl;

    @Value("${finnhub.api.key}")
    private String apiKey;

    public FinnhubCompanyProfileResponse getCompanyProfile(String symbol) {
        String url = String.format("%s/stock/profile2?symbol=%s&token=%s", baseUrl, symbol, apiKey);
        return restTemplate.getForObject(url, FinnhubCompanyProfileResponse.class);
    }

    public FinnhubStockQuoteResponse getQuote(String symbol) {
        String url = String.format("%s/quote?symbol=%s&token=%s", baseUrl, symbol, apiKey);
        return restTemplate.getForObject(url, FinnhubStockQuoteResponse.class);
    }
}
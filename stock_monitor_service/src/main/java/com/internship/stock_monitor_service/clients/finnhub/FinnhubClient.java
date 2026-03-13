package com.internship.stock_monitor_service.clients.finnhub;

import com.internship.stock_monitor_service.dtos.companies.FinnhubCompanyProfileResponse;
import com.internship.stock_monitor_service.dtos.stocks.FinnhubStockQuoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FinnhubClient {

    private final RestTemplate restTemplate;

    @Value("${finnhub.api.base-url}")
    private String baseUrl;

    @Value("${finnhub.api.key}")
    private String apiKey;

    public FinnhubCompanyProfileResponse getCompanyProfile(String symbol) {
        String url = baseUrl + "/stock/profile2?symbol={symbol}&token={token}";
        ResponseEntity<FinnhubCompanyProfileResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<FinnhubCompanyProfileResponse>() {},
                symbol,
                apiKey
        );
        return response.getBody();
    }

    public FinnhubStockQuoteResponse getQuote(String symbol) {
        String url = baseUrl + "/quote?symbol={symbol}&token={token}";

        ResponseEntity<FinnhubStockQuoteResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<FinnhubStockQuoteResponse>() {},
                symbol,
                apiKey
        );

        return response.getBody();
    }
}
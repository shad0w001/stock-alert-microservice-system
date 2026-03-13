package com.internship.stock_monitor_service.errors;

import result.ApiError;

public final class StockErrors {
    private StockErrors() {}

    public static ApiError notFound(String symbol) {
        return ApiError.notFound(
                "Stock.NotFound",
                "Stock quote for symbol '" + symbol + "' was not found."
        );
    }

    public static ApiError apiError(String symbol, String message) {
        return ApiError.problem(
                "Stock.ExternalApiError",
                "Failed to fetch quote from Finnhub for " + symbol + ": " + message
        );
    }
}

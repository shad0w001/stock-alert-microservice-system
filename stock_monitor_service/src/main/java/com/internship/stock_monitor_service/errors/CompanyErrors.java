package com.internship.stock_monitor_service.errors;

import result.ApiError;

public final class CompanyErrors {
    private CompanyErrors() {}

    public static ApiError notFound(String symbol) {
        return ApiError.notFound(
                "Company.NotFound",
                "Company with symbol '" + symbol + "' was not found."
        );
    }

    public static ApiError apiError(String symbol, String message) {
        return ApiError.problem(
                "Company.ExternalApiError",
                "Failed to fetch data from Finnhub for " + symbol + ": " + message
        );
    }
}
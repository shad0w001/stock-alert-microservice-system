package com.internship.stock_monitor_service.errors;

import result.ApiError;

public final class PriceHistoryErrors {
    private PriceHistoryErrors() {}

    public static ApiError noDataFound(String symbol) {
        return ApiError.notFound(
                "History.NoData",
                "No historical price data found for symbol: " + symbol
        );
    }
}

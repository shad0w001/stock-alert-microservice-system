package com.internship.stock_monitor_service.mappers;

import com.internship.stock_monitor_service.dtos.stocks.FinnhubStockQuoteResponse;
import com.internship.stock_monitor_service.dtos.stocks.StockViewDto;
import com.internship.stock_monitor_service.entities.StockQuote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {

    @Mapping(target = "symbol", source = "symbol")
    @Mapping(target = "currentPrice", source = "response.currentPrice")
    @Mapping(target = "priceChange", source = "response.change")
    @Mapping(target = "percentChange", source = "response.percentChange")
    @Mapping(target = "highPriceDay", source = "response.highDay")
    @Mapping(target = "lowPriceDay", source = "response.lowDay")
    @Mapping(target = "openPriceDay", source = "response.openDay")
    @Mapping(target = "prevClosePrice", source = "response.previousClose")
    @Mapping(target = "lastRefreshed", expression = "java(java.time.LocalDateTime.now())")
    StockQuote toEntity(FinnhubStockQuoteResponse response, String symbol);

    StockViewDto toViewDto(StockQuote entity);
}

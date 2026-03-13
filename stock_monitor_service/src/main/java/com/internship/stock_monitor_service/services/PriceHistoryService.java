package com.internship.stock_monitor_service.services;

import com.internship.stock_monitor_service.dtos.PriceHistoryViewDto;
import com.internship.stock_monitor_service.entities.PriceHistory;
import com.internship.stock_monitor_service.errors.PriceHistoryErrors;
import com.internship.stock_monitor_service.mappers.PriceHistoryMapper;
import com.internship.stock_monitor_service.repositories.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.Result;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;
    private final PriceHistoryMapper priceHistoryMapper;

    @Transactional(readOnly = true)
    public Result<List<PriceHistoryViewDto>> getHistoryForSymbol(String symbol) {
        String normalizedSymbol = symbol.toUpperCase();

        List<PriceHistory> history = priceHistoryRepository.findTop100BySymbolOrderByRecordedAtDesc(normalizedSymbol);

        if (history.isEmpty()) {
            return Result.failure(PriceHistoryErrors.noDataFound(normalizedSymbol));
        }

        return Result.success(priceHistoryMapper.toViewDtoList(history));
    }
}
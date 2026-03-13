package com.internship.stock_monitor_service.mappers;

import com.internship.stock_monitor_service.dtos.PriceHistoryViewDto;
import com.internship.stock_monitor_service.entities.PriceHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceHistoryMapper {

    PriceHistoryViewDto toViewDto(PriceHistory entity);

    List<PriceHistoryViewDto> toViewDtoList(List<PriceHistory> entities);
}
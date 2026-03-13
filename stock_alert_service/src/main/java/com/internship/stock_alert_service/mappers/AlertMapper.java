package com.internship.stock_alert_service.mappers;

import com.internship.stock_alert_service.dtos.alert.AlertCreateDto;
import com.internship.stock_alert_service.dtos.alert.AlertViewDto;
import com.internship.stock_alert_service.entities.Alert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlertMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Alert createDtoToEntity(AlertCreateDto dto);

    AlertViewDto toViewDto(Alert alert);
}

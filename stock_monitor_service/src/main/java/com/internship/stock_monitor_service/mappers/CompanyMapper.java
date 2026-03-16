package com.internship.stock_monitor_service.mappers;

import com.internship.stock_monitor_service.dtos.companies.CompanyViewDto;
import com.internship.stock_monitor_service.dtos.companies.FinnhubCompanyProfileResponse;
import com.internship.stock_monitor_service.entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "companyName", source = "name")
    @Mapping(target = "lastUpdated", expression = "java(java.time.LocalDateTime.now())")
    Company toEntity(FinnhubCompanyProfileResponse response);

    CompanyViewDto toViewDto(Company company);
}

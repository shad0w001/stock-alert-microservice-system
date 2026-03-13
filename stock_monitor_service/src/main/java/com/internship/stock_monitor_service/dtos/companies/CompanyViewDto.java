package com.internship.stock_monitor_service.dtos.companies;

import lombok.Data;

@Data
public class CompanyViewDto {
    private String symbol;
    private String companyName;
    private String country;
    private String industry;
    private String website;
}
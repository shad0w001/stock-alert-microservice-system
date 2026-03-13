package com.internship.stock_monitor_service.services;

import com.internship.stock_monitor_service.clients.finnhub.FinnhubClient;
import com.internship.stock_monitor_service.dtos.companies.CompanyViewDto;
import com.internship.stock_monitor_service.entities.Company;
import com.internship.stock_monitor_service.errors.CompanyErrors;
import com.internship.stock_monitor_service.mappers.CompanyMapper;
import com.internship.stock_monitor_service.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.Result;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final FinnhubClient finnhubClient;
    private final CompanyMapper companyMapper;

    @Transactional
    public Result<CompanyViewDto> getCompanyBySymbol(String symbol) {
        String normalizedSymbol = symbol.toUpperCase();

        var companyOpt = companyRepository.findById(normalizedSymbol);

        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();

            if (!isStale(company)) {
                log.info("Returning fresh company data from DB for: {}", normalizedSymbol);
                return Result.success(companyMapper.toViewDto(company));
            }

            log.info("Company data for {} is stale. Refreshing from API...", normalizedSymbol);

        } else {
            log.info("Company {} not found in DB. Fetching from API...", normalizedSymbol);
        }

        return refreshCompanyData(normalizedSymbol);
    }

    private Result<CompanyViewDto> refreshCompanyData(String symbol) {
        try {
            var response = finnhubClient.getCompanyProfile(symbol);

            if (response == null || response.getName() == null) {
                return Result.failure(CompanyErrors.notFound(symbol));
            }

            Company entity = companyMapper.toEntity(response);
            entity.setSymbol(symbol);

            Company saved = companyRepository.save(entity);
            return Result.success(companyMapper.toViewDto(saved));

        } catch (Exception e) {
            log.error("Finnhub integration error for {}: {}", symbol, e.getMessage());
            return Result.failure(CompanyErrors.apiError(symbol, e.getMessage()));
        }
    }

    private boolean isStale(Company company) {
        return company.getLastUpdated() == null ||
                company.getLastUpdated().isBefore(LocalDateTime.now().minusDays(1));
    }
}
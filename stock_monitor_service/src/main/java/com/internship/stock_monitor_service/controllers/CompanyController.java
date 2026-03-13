package com.internship.stock_monitor_service.controllers;

import com.internship.stock_monitor_service.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{symbol}")
    public ResponseEntity<Object> getCompanyProfile(@PathVariable String symbol) {
        var result = companyService.getCompanyBySymbol(symbol);

        if (result.isFailure()) {
            return ResponseEntity
                    .status(result.getError().status())
                    .body(result.getError());
        }

        return ResponseEntity.ok(result.getValue());
    }
}
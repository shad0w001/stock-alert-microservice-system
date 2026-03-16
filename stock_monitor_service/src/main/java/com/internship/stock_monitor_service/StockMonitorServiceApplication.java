package com.internship.stock_monitor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication(scanBasePackages = {
        "com.internship",
        "security",
        "result",
        "enums",
        "events",
        "topics"})
public class StockMonitorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockMonitorServiceApplication.class, args);
	}

}

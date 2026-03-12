package com.internship.stock_monitor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.internship", "security", "result", "enums"})
public class StockMonitorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockMonitorServiceApplication.class, args);
	}

}

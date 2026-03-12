package com.internship.stock_alert_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.internship", "security", "result", "enums"})
public class StockAlertServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockAlertServiceApplication.class, args);
	}

}

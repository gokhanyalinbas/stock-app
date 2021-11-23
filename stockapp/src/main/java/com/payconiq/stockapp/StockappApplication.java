package com.payconiq.stockapp;

import com.payconiq.stockapp.service.StockServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.payconiq.stockapp")
public class StockappApplication {

    static StockServiceImpl stockService;

    public StockappApplication(StockServiceImpl stockService) {
        StockappApplication.stockService = stockService;
    }

    public static void main(String[] args) {
        SpringApplication.run(StockappApplication.class, args);
        stockService.inMemoryDB();
    }


}

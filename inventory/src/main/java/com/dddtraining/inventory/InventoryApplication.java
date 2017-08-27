package com.dddtraining.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.dddtraining.inventory.application.command.CreateStockCommand;
import com.dddtraining.inventory.application.product.ProductApplicationService;
import com.dddtraining.inventory.application.stock.StockApplicationService;

@SpringBootApplication
public class InventoryApplication {

    private static final Logger logger = LoggerFactory
            .getLogger(InventoryApplication.class);

    public static void main(String[] args) {


		ConfigurableApplicationContext container = SpringApplication.run(InventoryApplication.class, args);
		
		ProductApplicationService productApplicationService = container.getBean(ProductApplicationService.class);
		StockApplicationService stockApplicationService = container.getBean(StockApplicationService.class);
		
		
		stockApplicationService.createStock(
				new CreateStockCommand("iiiii", "ppppppp", 9));

    }
}

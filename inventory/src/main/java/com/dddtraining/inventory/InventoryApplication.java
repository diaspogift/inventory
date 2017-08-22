package com.dddtraining.inventory;

import com.dddtraining.inventory.application.ProductApplicationService;
import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.application.command.RegisterProductCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import java.math.BigDecimal;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(InventoryApplication.class, args);

		ProductApplicationService productApplicationService =  ctx.getBean(ProductApplicationService.class);

		productApplicationService.addProduct(
				new RegisterProductCommand(
						"PROD_3333",
						"test",
						"descript"
				)
		);


	}
}

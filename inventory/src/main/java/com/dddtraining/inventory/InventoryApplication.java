package com.dddtraining.inventory;

import com.dddtraining.inventory.application.ProductApplicationService;
import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.application.command.RegisterProductCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.product.Product;
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





        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        Product registeredProduct = productApplicationService.product("PROD_3333");

        System.out.println("REGISTERED PRODUCT \n\n "+ registeredProduct);






        Arrivage arrivage = productApplicationService.addProductArrivage(
                new RegisterProductArrivageCommand(
                        "PROD_3333",
                        "ARR_PROD_3333",
                        50,
                        new BigDecimal(100),
                        "Test"
                )
        );




        productApplicationService.testReturnObject();

    }
}

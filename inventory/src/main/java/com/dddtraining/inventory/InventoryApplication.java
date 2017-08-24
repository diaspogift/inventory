package com.dddtraining.inventory;

import com.dddtraining.inventory.application.ProductApplicationService;
import com.dddtraining.inventory.application.StockApplicationService;
import com.dddtraining.inventory.application.command.CreateProductStockCommand;
import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.application.command.RegisterProductCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockProductArrivage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import java.math.BigDecimal;
import java.util.Set;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(InventoryApplication.class, args);

        ProductApplicationService productApplicationService =  ctx.getBean(ProductApplicationService.class);
        StockApplicationService stockApplicationService =  ctx.getBean(StockApplicationService.class);

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

        System.out.println("REGISTERED PRODUCT WITH NO STOCK  \n\n "+ registeredProduct);



        productApplicationService.creatProductStock(
                new CreateProductStockCommand(
                        "STOCK_ID_52",
                        registeredProduct.productId().id(),
                        1000
                )
        );


        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        registeredProduct = productApplicationService.product("PROD_3333");

        System.out.println("REGISTERED PRODUCT  WITH STOCK ASSIGNED\n\n "+ registeredProduct);



        Stock stock =
                stockApplicationService.stock(registeredProduct.stockId().id());



        System.out.println("REGISTERED PRODUCT STOCK WITH NO ARRIVAGE \n\n "+ stock);





        Arrivage arrivage = productApplicationService.addProductArrivage(
                new RegisterProductArrivageCommand(
                        "PROD_3333",
                        "ARR_PROD_3333",
                        1,
                        new BigDecimal(100),
                        "Test arr1"
                )
        );


        Arrivage arrivage2 = productApplicationService.addProductArrivage(
                        new RegisterProductArrivageCommand(
                                "PROD_3333",
                                "ARR_PROD_3334",
                                2,
                                new BigDecimal(200),
                                "Test arr1"
                        )
                );


        Arrivage arrivage3 = productApplicationService.addProductArrivage(
                new RegisterProductArrivageCommand(
                        "PROD_3333",
                        "ARR_PROD_3335",
                        3,
                        new BigDecimal(50),
                        "Test arr1"
                )
        );





        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Stock stock1 =
                stockApplicationService.stock(registeredProduct.stockId().id());



        System.out.println("REGISTERED PRODUCT STOCK WITH ONE ARRIVAGE \n\n "+ stock1);

    }
}

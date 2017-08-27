package com.dddtraining.inventory.application.stock;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.application.command.CreateStockCommand;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockApplicationServiceTest {

    @Autowired
    private StockApplicationService stockApplicationService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArrivageRepository arrivageRepository;


    @Test
    public void testAddStock() {

        CreateStockCommand createStockCommand =
                new CreateStockCommand(
                        "STOCK_OF_ID_1",
                        "PROD_OF_ID_1",
                        1000
                );


        this.stockApplicationService
                .createStock(createStockCommand);


        Stock stock =
                this.stockApplicationService
                        .stock(createStockCommand.stockId());

        assertNotNull(stock);
        assertEquals(new ProductId("PROD_OF_ID_1"), stock.productId());
        assertEquals(new Quantity(1000), stock.quantity());


    }
}

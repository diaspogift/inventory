package com.dddtraining.inventory.application;


import com.dddtraining.inventory.application.command.AugmentProductStockCommand;
import com.dddtraining.inventory.application.command.DecrementProductStockCommand;
import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.application.command.RegisterProductCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import com.dddtraining.inventory.infrastructure.persistence.MockProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationServiceTest {


    @Autowired
    private ProductApplicationService productApplicationService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArrivageRepository arrivageRepository;
    @Autowired
    private StockRepository stockRepository;



    @Test
    public void testAddNewProduct(){



        RegisterProductCommand registerProductCommand =
                new RegisterProductCommand(
                        "PROD_OF_ID_1",
                        "Product name",
                        "Product desciption"
                        );

        productApplicationService.addProduct(registerProductCommand);

        Product newProduct =
                this.productRepository
                        .productOfId(
                                new ProductId("PROD_OF_ID_1"));

        assertNotNull(newProduct);
        assertEquals("Product name", newProduct.name());
        assertEquals("Product desciption", newProduct.description());


    }

    @Test
    public void testAddProductArrivage(){


        RegisterProductArrivageCommand registerProductArrivageCommand1 =
                new RegisterProductArrivageCommand(
                    "PRODUCT_ID_1",
                     "ARRI_OF_ID_N",
                        100,
                        new BigDecimal(1500),
                        "Arrivage de noel"
                );



        productApplicationService.addProductArrivage(registerProductArrivageCommand1);

        Set<Arrivage> arrivages =
                this.arrivageRepository
                        .allArrivagesOfProductId(
                                new ProductId(
                                        "PRODUCT_ID_1")
                );

        assertNotNull(arrivages);
        assertEquals(1,arrivages.size());
    }


    @Test
    public void testDecrementProductStock(){

        DecrementProductStockCommand decrementProductStockCommand =
                new DecrementProductStockCommand(
                        155,
                        "PRODUCT_ID_2");

        Stock stock1 =
                this.stockRepository
                .stockForProductOfId(
                        new ProductId(decrementProductStockCommand.productId()));

        assertNotNull(stock1);
        assertEquals(new Quantity(200), stock1.quantity());

        this.productApplicationService
                .decrementProductStock(
                        decrementProductStockCommand);

        Stock stock2 =
                this.stockRepository
                        .stockForProductOfId(
                                new ProductId(decrementProductStockCommand.productId()));

        assertNotNull(stock2);
        assertEquals(new Quantity(45), stock2.quantity());


    }

    @Test
    public void testAugmentProductStock(){

        AugmentProductStockCommand augmentProductStockCommand =
                new AugmentProductStockCommand(
                        "PRODUCT_ID_1",
                        200);

        Stock stock1 =
                this.stockRepository
                        .stockForProductOfId(
                                new ProductId(augmentProductStockCommand.productId()));

        assertNotNull(stock1);
        assertEquals(new Quantity(200), stock1.quantity());

        this.productApplicationService
                .augmentProductStock(
                        augmentProductStockCommand);

        Stock stock2 =
                this.stockRepository
                        .stockForProductOfId(
                                new ProductId(augmentProductStockCommand.productId()));

        assertNotNull(stock2);
        assertEquals(new Quantity(400), stock2.quantity());


    }
}

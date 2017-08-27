package com.dddtraining.inventory.domain.model.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dddtraining.inventory.DomainTest;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.NewArrivageCreated;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockCreated;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockProductArrivage;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductTest extends DomainTest {


    @Test
    public void testCreateProduct() {
    	
    	
    	


        Product product =
                new Product(
                        this.productRepository.nextIdentity(),
                        "My product name",
                        "Nails for soft walls");


        this.productRepository.add(product);
        
        Product productFound = 
        		this.productRepository.productOfId(product.productId());

        assertNotNull(product);
        assertNotNull(productFound);
        assertEquals(product.productId(), productFound.productId());
        assertEquals("My product name", product.name());
        assertEquals("Nails for soft walls", product.description());
        assertEquals(AvailabilityStatus.CREATED, product.status());
        assertEquals(product, productFound);
        
        
        this.expectedEvents(1);
        this.expectedEvent(ProductCreated.class);


    }


    @Test
    public void testChangeAvailabilityStatus() {

        Product product =
                new Product(
                        new ProductId("P12345"),
                        "My product name",
                        "Nails for soft walls");

        assertEquals(AvailabilityStatus.CREATED, product.status());

        product.changeAvailabilityStatus(AvailabilityStatus.STOCK_PROVIDED);
        assertEquals(AvailabilityStatus.STOCK_PROVIDED, product.status());


        product.changeAvailabilityStatus(AvailabilityStatus.THRESHOLD_REACHED);
        assertEquals(AvailabilityStatus.THRESHOLD_REACHED, product.status());


        product.changeAvailabilityStatus(AvailabilityStatus.STOCK_CLEARED);
        assertEquals(AvailabilityStatus.STOCK_CLEARED, product.status());

        this.expectedEvents(1);
        this.expectedEvent(ProductCreated.class);

    }


    @Test
    public void createNewArrivage() {


        Product product =
                new Product(
                        new ProductId("P12345"),
                        new StockId("STOCK_ID_1"),
                        "My product name",
                        "Nails for soft walls");


        Arrivage arrivage =
                product.createNewArrivage(
                        new ArrivageId("ARR12345"),
                        new Quantity(10),
                        new BigDecimal(5.5), "Ciment Dangote");
        Arrivage arrivage2 =
                product.createNewArrivage(
                        new ArrivageId("ARR12346"),
                        new Quantity(100),
                        new BigDecimal(5.56), "Ciment Dangote new");

        assertEquals(product.productId(), arrivage.productId());
        assertEquals(product.productId(), arrivage2.productId());


        this.expectedEvents(3);
        this.expectedEvent(ProductCreated.class);
        this.expectedEvent(NewArrivageCreated.class, 2);

    }

    @Test
    public void createStock() {

        Product product =
                new Product(
                        new ProductId("P12345"),
                        "My product name",
                        "Nails for soft walls");

        Stock stockForProduct = product.createStock(new StockId("ST12345"), 500);

        assertEquals(stockForProduct.productId(), product.productId());
        assertEquals(500, stockForProduct.threshold());
        assertEquals(new Quantity(0), stockForProduct.quantity());


        this.expectedEvents(2);
        this.expectedEvent(ProductCreated.class);
        this.expectedEvent(StockCreated.class);

    }

    @Test
    public void testReorderFrom() throws Exception {

        Stock stock =
                new Stock(
                        new StockId("STOCK_ID_1"),
                        new ProductId("PROD_ID_1")
                );

        Arrivage arrivage1 =
                new Arrivage(
                        new ProductId("PROD_ID_1"),
                        new StockId("STOCK_ID_1"),
                        new ArrivageId("ARR_ID_1"),
                        new Quantity(100),
                        new BigDecimal(100),
                        "Des"
                );

        Arrivage arrivage2 =
                new Arrivage(
                        new ProductId("PROD_ID_1"),
                        new StockId("STOCK_ID_1"),
                        new ArrivageId("ARR_ID_2"),
                        new Quantity(100),
                        new BigDecimal(100),
                        "Des"
                );
        Arrivage arrivage3 =
                new Arrivage(
                        new ProductId("PROD_ID_1"),
                        new StockId("STOCK_ID_1"),
                        new ArrivageId("ARR_ID_3"),
                        new Quantity(100),
                        new BigDecimal(100),
                        "Des"
                );

        stock.addNewStockProductArrivage(arrivage1);
        stock.addNewStockProductArrivage(arrivage2);
        stock.addNewStockProductArrivage(arrivage3);


        StockProductArrivage stockProductArrivage1 = null;
        StockProductArrivage stockProductArrivage2 = null;
        StockProductArrivage stockProductArrivage3 = null;

        for (StockProductArrivage stockProductArrivage : stock.stockProductArrivages()) {
            if (stockProductArrivage.ordering() == 1) {
                stockProductArrivage1 = stockProductArrivage;
            }
            if (stockProductArrivage.ordering() == 2) {
                stockProductArrivage2 = stockProductArrivage;
            }
            if (stockProductArrivage.ordering() == 3) {
                stockProductArrivage3 = stockProductArrivage;
            }
        }

        stock.reorderFrom(stockProductArrivage3.arrivageId(), 1);

        assertEquals(1, stockProductArrivage3.ordering());
        assertEquals(2, stockProductArrivage1.ordering());
        assertEquals(3, stockProductArrivage2.ordering());

        assertEquals(3, stock.stockProductArrivages().size());

        assertEquals(300, stock.quantity().value());


        this.expectedEvents(1);
        this.expectedEvent(StockCreated.class, 1);

    }


}

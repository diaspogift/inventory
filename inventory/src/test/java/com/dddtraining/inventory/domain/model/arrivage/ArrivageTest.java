package com.dddtraining.inventory.domain.model.arrivage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dddtraining.inventory.DomainTest;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductCreated;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockCreated;
import com.dddtraining.inventory.domain.model.stock.StockId;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ArrivageTest extends DomainTest{


	
	//TO O Arrivage should have his domain event in his constructor
    @Test
    public void testCreateArrivage() {
    	
    	
    	Product product =
                new Product(
                        this.productRepository.nextIdentity(),
                        "My product name",
                        "Nails for soft walls");


        this.productRepository.add(product);
        
        
        Stock stock =
                new Stock(
                		this.stockRepository.nextIdentity(),
                		product.productId());
        
        

        this.stockRepository.add(stock);
        
        
        Stock stockFound = 
        		this.stockRepository.stockOfId(stock.stockId());
        
        
        ArrivageId arrivageId =
        		this.arrivageRepository.nextIdentity();

        Arrivage arrivage =
                new Arrivage(
                		product.productId(),
                		product.stockId(),
                        arrivageId,
                        new Quantity(1000),
                        new BigDecimal(500),
                        "Arrivage de la periode des fetes");
        
        this.arrivageRepository.add(arrivage);
        
        
        Arrivage arrivageFound = this.arrivageRepository.arrivgeOfId(arrivageId);
        
 

        assertNotNull(arrivage);
        assertNotNull(arrivageFound);
        assertEquals(arrivage, arrivageFound);

        assertEquals(product.productId(), arrivage.productId());
        assertEquals(product.stockId(), arrivage.stockId());
        assertEquals(new Quantity(1000), arrivage.quantity());
        assertEquals(new BigDecimal(500), arrivage.uniPrice());
        assertEquals("Arrivage de la periode des fetes", arrivage.description());
        
        this.expectedEvent(ProductCreated.class);
        this.expectedEvent(StockCreated.class);
        this.expectedEvents(2);


    }

    @Test
    public void testChangeUnitPrice() {

        Arrivage arrivage =
                new Arrivage(
                        new ProductId("PR12345"),
                        new StockId("STOCK_ID_2"),
                        new ArrivageId("ARR12345"),
                        new Quantity(0),
                        new BigDecimal(500),
                        "Arrivage de la periode des fetes");

        assertEquals(new BigDecimal(500), arrivage.uniPrice());

        arrivage.changeUnitPrice(new BigDecimal(550));

        assertEquals(new BigDecimal(550), arrivage.uniPrice());

    }

    @Test
    public void testChangeQuantity() {

        Arrivage arrivage =
                new Arrivage(
                        new ProductId("PR12345"),
                        new StockId("STOCK_ID_2"),
                        new ArrivageId("ARR12345"),
                        new Quantity(1000),
                        new BigDecimal(500),
                        "Arrivage de la periode des fetes");

        assertEquals(new Quantity(1000), arrivage.quantity());

        arrivage.changeQuantity(2000);


        assertEquals(new Quantity(2000), arrivage.quantity());
        
        this.expectedEvents(1);
        this.expectedEvent(ArrivageQuantityChanged.class);

    }
}

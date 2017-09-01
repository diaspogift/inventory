package com.dddtraining.inventory.domain.model.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dddtraining.inventory.InventoryApplication;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.NewArrivageCreated;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductCreated;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductStockAssigned;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StockTest extends StockCommonTest {


    private static final Logger logger = LoggerFactory
            .getLogger(InventoryApplication.class);
    @Test
    public void testCreateStock() {



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

        
        assertNotNull(stock);
        assertEquals(stockFound.stockId(), stock.stockId());
        assertEquals(stockFound.productId(), stock.productId());
        assertEquals(new Quantity(0), stock.quantity());
        assertEquals(0, stock.threshold());


        this.expectedEvents(2);
        this.expectedEvent(ProductCreated.class, 1);
        this.expectedEvent(StockCreated.class, 1);


    }


    @Test
    public void testClearStockOfNoThresholdReached() {


    	Stock stock = this.StockWithThreeProductArrivages();


        assertEquals(new Quantity(550), stock.quantity());
        assertNotNull(stock.stockProductArrivages());
        assertEquals(3, stock.stockProductArrivages().size());

        //First clear
        stock.clearStockOf(500);


        StockProductArrivage supposellyModifiedArrivage1 = null;
        StockProductArrivage supposellyModifiedArrivage2 = null;
        StockProductArrivage supposellyModifiedArrivage3 = null;

        for (StockProductArrivage next : stock.stockProductArrivages()) {

            if (next.ordering() == 1) {

                supposellyModifiedArrivage1 = next;
            }
            if (next.ordering() == 2) {

                supposellyModifiedArrivage2 = next;
            }
            if (next.ordering() == 3) {

                supposellyModifiedArrivage3 = next;
            }
        }

        assertEquals(new Quantity(0), supposellyModifiedArrivage1.quantity());
        assertEquals(new Quantity(0), supposellyModifiedArrivage2.quantity());
        assertEquals(new Quantity(50), supposellyModifiedArrivage3.quantity());
        assertEquals(new Quantity(50), stock.quantity());




        this.expectedEvents(2);
        this.expectedEvent(StockCreated.class, 1);
        this.expectedEvent(StockQuantityChanged.class, 1);


    }

    @Test
    public void testClearStockOfWithExactThresholdReached() {

    	
    	 Stock stock =
                 new Stock(
                         new StockId("STOCK_ID_1"),
                         new ProductId("PROD_ID_1"),
                         new Quantity(0),
                         20
                    
                 );

         Arrivage arrivage1 =
                 new Arrivage(
                         new ProductId("PROD_ID_1"),
                         new StockId("STOCK_ID_1"),
                         new ArrivageId("ARR_ID_1"),
                         new Quantity(10),
                         new BigDecimal(105),
                         "Des"
                 );

         Arrivage arrivage2 =
                 new Arrivage(
                         new ProductId("PROD_ID_1"),
                         new StockId("STOCK_ID_1"),
                         new ArrivageId("ARR_ID_2"),
                         new Quantity(30),
                         new BigDecimal(110),
                         "Des"
                 );
         Arrivage arrivage3 =
                 new Arrivage(
                         new ProductId("PROD_ID_1"),
                         new StockId("STOCK_ID_1"),
                         new ArrivageId("ARR_ID_3"),
                         new Quantity(10),
                         new BigDecimal(85),
                         "Des"
                 );

         stock.addNewStockProductArrivage(arrivage1);
         stock.addNewStockProductArrivage(arrivage2);
         stock.addNewStockProductArrivage(arrivage3);



        assertEquals(new Quantity(50), stock.quantity());
        assertEquals(20, stock.threshold());
        assertEquals(false, stock.isthesholdReached());


        stock.clearStockOf(40);

        assertEquals(true, stock.isthesholdReached());


        assertEquals(new Quantity(10), stock.quantity());


        this.expectedEvents(3);
        this.expectedEvent(StockCreated.class);
        this.expectedEvent(StockQuantityChanged.class);
        this.expectedEvent(StockThresholdReached.class);

    }

    @Test
    public void testClearStockOfWholeQuantityWithTrhesholdNotReached() {


    	

   	 Stock stock =
                new Stock(
                        new StockId("STOCK_ID_1"),
                        new ProductId("PROD_ID_1"),
                        new Quantity(0),
                        20
                   
                );

        Arrivage arrivage1 =
                new Arrivage(
                        new ProductId("PROD_ID_1"),
                        new StockId("STOCK_ID_1"),
                        new ArrivageId("ARR_ID_1"),
                        new Quantity(10),
                        new BigDecimal(105),
                        "Des"
                );

        Arrivage arrivage2 =
                new Arrivage(
                        new ProductId("PROD_ID_1"),
                        new StockId("STOCK_ID_1"),
                        new ArrivageId("ARR_ID_2"),
                        new Quantity(30),
                        new BigDecimal(110),
                        "Des"
                );
        Arrivage arrivage3 =
                new Arrivage(
                        new ProductId("PROD_ID_1"),
                        new StockId("STOCK_ID_1"),
                        new ArrivageId("ARR_ID_3"),
                        new Quantity(10),
                        new BigDecimal(85),
                        "Des"
                );

        stock.addNewStockProductArrivage(arrivage1);
        stock.addNewStockProductArrivage(arrivage2);
        stock.addNewStockProductArrivage(arrivage3);

   

        assertEquals(new Quantity(50), stock.quantity());
        assertEquals(20, stock.threshold());


        stock.clearStockOf(50);


        assertEquals(new Quantity(0), stock.quantity());


        this.expectedEvents(4);
        this.expectedEvent(StockCreated.class);
        this.expectedEvent(StockThresholdReached.class);
        this.expectedEvent(StockQuantityChanged.class);
        this.expectedEvent(StockEmptied.class);

    }


    @Test
    public void testClearStockOfWholeQuantityWithTrhesholdAlreadyReached() {


        Stock stock =
                new Stock(
                        new StockId("ST12345"),
                        new ProductId("PR12345"),
                        new Quantity(15),
                        20);

        assertEquals(new Quantity(15), stock.quantity());
        assertEquals(20, stock.threshold());


        stock.clearStockOf(5);


        assertEquals(new Quantity(10), stock.quantity());


        this.expectedEvents(2);
        this.expectedEvent(StockCreated.class);

    }


    @Test
    public void testUnavailableStock() {

        Stock stock =
                new Stock(
                        new StockId("ST12345"),
                        new ProductId("PR12345"));

        assertEquals(false, stock.isAvailable());

        stock.unAvailable();

        assertEquals(false, stock.isAvailable());

        this.expectedEvents(1);
        this.expectedEvent(StockCreated.class);

    }



    @Test
    public void testAdjustThreshold() {

        Stock stock =
                new Stock(
                        new StockId("ST12345"),
                        new ProductId("PR12345"));

        assertEquals(0, stock.threshold());

        stock.adjustThreshold(100);

        assertEquals(100, stock.threshold());

        this.expectedEvents(1);
        this.expectedEvent(StockCreated.class);

    }

    @Test
    public void testAddNewStockProductArrivage() {


        Product product =
                new Product(
                        new ProductId("P12345"),
                        "My product name",
                        "Nails for soft walls");

        Stock stock = product.createStock(new StockId("ST12345"), 500);

        product.assignStock(stock);

        assertEquals(0, stock.stockProductArrivages().size());

        Arrivage arrivage = product.createNewArrivage(
                new ArrivageId("ARR12345"),
                new Quantity(5),
                new BigDecimal(500),
                "Arrivage Description");

        stock.addNewStockProductArrivage(arrivage);


        this.expectedEvents(4);
        this.expectedEvent(ProductCreated.class);
        this.expectedEvent(ProductStockAssigned.class);
        this.expectedEvent(NewArrivageCreated.class);
        this.expectedEvent(StockCreated.class);


    }
    
    @Test
    public void testStockUpdateProductArrivage(){
    	
      	 Stock stock =
                 new Stock(
                         new StockId("STOCK_ID_1"),
                         new ProductId("PROD_ID_1"),
                         new Quantity(0),
                         20
                    
                 );

         Arrivage arrivage1 =
                 new Arrivage(
                         new ProductId("PROD_ID_1"),
                         new StockId("STOCK_ID_1"),
                         new ArrivageId("ARR_ID_1"),
                         new Quantity(10),
                         new BigDecimal(105),
                         "Des"
                 );

         Arrivage arrivage2 =
                 new Arrivage(
                         new ProductId("PROD_ID_1"),
                         new StockId("STOCK_ID_1"),
                         new ArrivageId("ARR_ID_2"),
                         new Quantity(30),
                         new BigDecimal(110),
                         "Des"
                 );
         Arrivage arrivage3 =
                 new Arrivage(
                         new ProductId("PROD_ID_1"),
                         new StockId("STOCK_ID_1"),
                         new ArrivageId("ARR_ID_3"),
                         new Quantity(10),
                         new BigDecimal(85),
                         "Des"
                 );

         stock.addNewStockProductArrivage(arrivage1);
         stock.addNewStockProductArrivage(arrivage2);
         stock.addNewStockProductArrivage(arrivage3);
         
         

    

         assertEquals(new Quantity(50), stock.quantity());
         assertEquals(20, stock.threshold());




         StockProductArrivage stockProductArrivage = 
        		 new StockProductArrivage(
        				 arrivage3.arrivageId(),
        				 new Quantity(60));
         
         stock.updateProductArrivage(stockProductArrivage);
         
         
         
         StockProductArrivage supposellyNotModifiedArrivage1 = null;
         StockProductArrivage supposellyNotModifiedArrivage2 = null;
         StockProductArrivage supposellyModifiedArrivage3 = null;

         for (StockProductArrivage next : stock.stockProductArrivages()) {



             if (next.ordering() == 1) {

                 supposellyNotModifiedArrivage1 = next;
             }
             else if (next.ordering() == 2) {

                 supposellyNotModifiedArrivage2 = next;
             }
             else if (next.ordering() == 3) {

            	 

                 supposellyModifiedArrivage3 = next;
             }
         }



         
         assertEquals(new Quantity(100), stock.quantity());
         assertEquals(arrivage3.arrivageId(), supposellyModifiedArrivage3.arrivageId());
         assertEquals(arrivage3.lifeSpanTime(), supposellyModifiedArrivage3.lifeSpanTime());
         assertEquals(new Quantity(60), supposellyModifiedArrivage3.quantity());
         assertEquals(arrivage3.productId(), supposellyModifiedArrivage3.productId());
         assertEquals(3, supposellyModifiedArrivage3.ordering());
         
         
         assertEquals(20, stock.threshold());


         this.expectedEvents(1);
         this.expectedEvent(StockCreated.class);
    	
    }

}

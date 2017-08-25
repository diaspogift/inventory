package com.dddtraining.inventory.domain.model.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.common.DomainEventSubscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StockTest {
	
	
	@Test
	public void testCreateStock(){



				
		Stock stock = 
				new Stock(
						new StockId("ST12345"), 
						new ProductId("PR12345"));
				
		assertNotNull(stock);
		assertEquals(new StockId("ST12345"),  stock.stockId());
		assertEquals(new ProductId("PR12345"),  stock.productId());
		assertEquals(new Quantity(0), stock.quantity());



		
	}
	
	
	@Test
	public void testClearStockOfNoThresholdReached(){


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
                        new Quantity(300),
                        new BigDecimal(105),
                        "Des"
                );

        Arrivage arrivage2 =
                new Arrivage(
                        new ProductId("PROD_ID_1"),
                        new StockId("STOCK_ID_1"),
                        new ArrivageId("ARR_ID_2"),
                        new Quantity(150),
                        new BigDecimal(110),
                        "Des"
                );
        Arrivage arrivage3 =
                new Arrivage(
                        new ProductId("PROD_ID_1"),
                        new StockId("STOCK_ID_1"),
                        new ArrivageId("ARR_ID_3"),
                        new Quantity(100),
                        new BigDecimal(85),
                        "Des"
                );

        stock.addNewStockProductArrivage(arrivage1);
        stock.addNewStockProductArrivage(arrivage2);
        stock.addNewStockProductArrivage(arrivage3);



		
		assertEquals(new Quantity(550), stock.quantity());
		assertNotNull(stock.stockProductArrivages());
		assertEquals(3, stock.stockProductArrivages().size());


		//First clear

        stock.clearStockOf(100);


        StockProductArrivage supposellyModifiedArrivage1 = null;
        StockProductArrivage supposellyModifiedArrivage2 = null;
        StockProductArrivage supposellyModifiedArrivage3 = null;

        for(StockProductArrivage next : stock.stockProductArrivages()){

            if (next.ordering() == 1){

                supposellyModifiedArrivage1 = next;
            }
            if (next.ordering() == 2){

                supposellyModifiedArrivage2 = next;
            }
            if (next.ordering() == 3){

                supposellyModifiedArrivage3 = next;
            }
        }

        assertEquals(new Quantity(200), supposellyModifiedArrivage1.quantity());
        assertEquals(new Quantity(150), supposellyModifiedArrivage2.quantity());
        assertEquals(new Quantity(100), supposellyModifiedArrivage3.quantity());
        assertEquals(new Quantity(450), stock.quantity());



        //Second clear
        stock.clearStockOf(250);


         supposellyModifiedArrivage1 = null;
         supposellyModifiedArrivage2 = null;
         supposellyModifiedArrivage3 = null;

        for(StockProductArrivage next : stock.stockProductArrivages()){

            if (next.ordering() == 1){

                supposellyModifiedArrivage1 = next;
            }
            if (next.ordering() == 2){

                supposellyModifiedArrivage2 = next;
            }
            if (next.ordering() == 3){

                supposellyModifiedArrivage3 = next;
            }
        }

        assertEquals(new Quantity(0), supposellyModifiedArrivage1.quantity());
        assertEquals(new Quantity(100), supposellyModifiedArrivage2.quantity());
        assertEquals(new Quantity(100), supposellyModifiedArrivage3.quantity());
        assertEquals(new Quantity(200), stock.quantity());

        //Third clear
        stock.clearStockOf(150);


        supposellyModifiedArrivage1 = null;
        supposellyModifiedArrivage2 = null;
        supposellyModifiedArrivage3 = null;

        for(StockProductArrivage next : stock.stockProductArrivages()){

            if (next.ordering() == 1){

                supposellyModifiedArrivage1 = next;
            }
            if (next.ordering() == 2){

                supposellyModifiedArrivage2 = next;
            }
            if (next.ordering() == 3){

                supposellyModifiedArrivage3 = next;
            }
        }

        assertEquals(new Quantity(0), supposellyModifiedArrivage1.quantity());
        assertEquals(new Quantity(0), supposellyModifiedArrivage2.quantity());
        assertEquals(new Quantity(50), supposellyModifiedArrivage3.quantity());
        assertEquals(new Quantity(50), stock.quantity());

//
//
//		stock.clearStockOf(5);
//
//		assertEquals(new Quantity(40), stock.quantity());

	}

    @Test
    public void testClearStockOfWithExactThresholdReached(){



        //TO BE REFACTORED
        List<Class<? extends DomainEvent>> handledEvents = new ArrayList<Class<? extends DomainEvent>>();

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {
            @Override
            public void handleEvent(DomainEvent aDomainEvent) {
                handledEvents.add(aDomainEvent.getClass());
            }

            @Override
            public Class<DomainEvent> subscribedToEventType() {
                return DomainEvent.class;
            }
        });



        DomainEventSubscriber<StockThresholdReached> domainEventSubscriber = new DomainEventSubscriber<StockThresholdReached>() {
            @Override
            public void handleEvent(StockThresholdReached aDomainEvent) {
                System.out.println(aDomainEvent.toString());
            }

            @Override
            public Class<StockThresholdReached> subscribedToEventType() {
                return StockThresholdReached.class;
            }
        };

        DomainEventPublisher.instance().subscribe(domainEventSubscriber);

        //TO BE REFACTORED


        Stock stock =
                new Stock(
                        new StockId("ST12345"),
                        new ProductId("PR12345"),
                        new Quantity(50),
                        20);

        assertEquals(new Quantity(50), stock.quantity());
        assertEquals(20, stock.threshold());
        assertEquals(false, stock.isthesholdReached());


        stock.clearStockOf(40);

        assertEquals(true, stock.isthesholdReached());




        assertEquals(new Quantity(10), stock.quantity());




        assertEquals(2, handledEvents.size());
        assertEquals(handledEvents.get(0), StockCreated.class);
        assertEquals(handledEvents.get(1), StockThresholdReached.class);

    }

    @Test
    public void testClearStockOfWholeQuantityWithTrhesholdNotReached(){

        //TO BE REFACTORED
        List<Class<? extends DomainEvent>> handledEvents = new ArrayList<Class<? extends DomainEvent>>();

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {
            @Override
            public void handleEvent(DomainEvent aDomainEvent) {
                handledEvents.add(aDomainEvent.getClass());
            }

            @Override
            public Class<DomainEvent> subscribedToEventType() {
                return DomainEvent.class;
            }
        });



        DomainEventSubscriber<StockEmptied> domainEventSubscriber = new DomainEventSubscriber<StockEmptied>() {
            @Override
            public void handleEvent(StockEmptied aDomainEvent) {
                System.out.println(aDomainEvent.toString());
            }

            @Override
            public Class<StockEmptied> subscribedToEventType() {
                return StockEmptied.class;
            }
        };

        DomainEventPublisher.instance().subscribe(domainEventSubscriber);

        //TO BE REFACTORED


        Stock stock =
                new Stock(
                        new StockId("ST12345"),
                        new ProductId("PR12345"),
                        new Quantity(50),
                        20);

        assertEquals(new Quantity(50), stock.quantity());
        assertEquals(20, stock.threshold());


        stock.clearStockOf(50);





        assertEquals(new Quantity(0), stock.quantity());




        assertEquals(3, handledEvents.size());
        assertEquals(handledEvents.get(0), StockCreated.class);
        assertEquals(handledEvents.get(1), StockThresholdReached.class);
        assertEquals(handledEvents.get(2), StockEmptied.class);

    }


    @Test
    public void testClearStockOfWholeQuantityWithTrhesholdReached(){

        //TO BE REFACTORED
        List<Class<? extends DomainEvent>> handledEvents = new ArrayList<Class<? extends DomainEvent>>();

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {
            @Override
            public void handleEvent(DomainEvent aDomainEvent) {
                handledEvents.add(aDomainEvent.getClass());
            }

            @Override
            public Class<DomainEvent> subscribedToEventType() {
                return DomainEvent.class;
            }
        });



        DomainEventSubscriber<StockEmptied> domainEventSubscriber = new DomainEventSubscriber<StockEmptied>() {
            @Override
            public void handleEvent(StockEmptied aDomainEvent) {
                System.out.println(aDomainEvent.toString());
            }

            @Override
            public Class<StockEmptied> subscribedToEventType() {
                return StockEmptied.class;
            }
        };

        DomainEventPublisher.instance().subscribe(domainEventSubscriber);

        //TO BE REFACTORED


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




        assertEquals(1, handledEvents.size());
        assertEquals(handledEvents.get(0), StockCreated.class);

    }



    @Test
	public void testUnavailableStock(){
				
		Stock stock = 
				new Stock(
						new StockId("ST12345"), 
						new ProductId("PR12345"));
				
		assertEquals(true, stock.isAvailable());
		
		stock.unAvailable();
		
		assertEquals(false,  stock.isAvailable());
		
	}

	



	@Test
	public void testThresholdReachedOn(){

		Stock stock =
				new Stock(
						new StockId("ST12345"),
						new ProductId("PR12345"));

		assertEquals(null, stock.dateStockThresholdReached());

		ZonedDateTime dateStockThresholdReached = ZonedDateTime.now();

		stock.thresholdReachedOn(dateStockThresholdReached);

		assertEquals(dateStockThresholdReached, stock.dateStockThresholdReached());



	}

	@Test
	public void testAdjustThreshold(){

		Stock stock =
				new Stock(
						new StockId("ST12345"),
						new ProductId("PR12345"));

		assertEquals(0, stock.threshold());

		stock.adjustThreshold(100);

		assertEquals(100, stock.threshold());


	}

    @Test
    public void testAddNewStockProductArrivage(){



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

        assertEquals(1, stock.stockProductArrivages().size());


    }

}

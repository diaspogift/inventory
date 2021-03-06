package com.dddtraining.inventory.domain.model.product;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dddtraining.inventory.domain.model.arrivage.NewArrivageCreated;
import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.common.DomainEventSubscriber;
import com.dddtraining.inventory.domain.model.stock.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {



	
	@Test
	public void testCreateProduct(){



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



		DomainEventSubscriber<ProductCreated> domainEventSubscriber = new DomainEventSubscriber<ProductCreated>() {
			@Override
			public void handleEvent(ProductCreated aDomainEvent) {
				System.out.println(aDomainEvent.toString());
			}

			@Override
			public Class<ProductCreated> subscribedToEventType() {
				return ProductCreated.class;
			}
		};

		DomainEventPublisher.instance().subscribe(domainEventSubscriber);

		//TO BE REFACTORED



		Product product = 
				new Product(
						new ProductId("P12345"),
						"My product name",
						"Nails for soft walls");
		
		
		//TOD DO save to repository
		
		assertNotNull(product);
		assertEquals(new ProductId("P12345"), product.productId());
		assertEquals("My product name", product.name());
		assertEquals("Nails for soft walls", product.description());
		assertEquals(AvailabilityStatus.CREATED, product.status());


		assertEquals(1, handledEvents.size());
		assertEquals(handledEvents.get(0), ProductCreated.class);
		
	}
	


	@Test
	public void testChangeAvailabilityStatus(){

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
		
	}
	


	
	@Test
	public void createNewArrivage(){


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


        DomainEventSubscriber<NewArrivageCreated> domainEventSubscriber1 = new DomainEventSubscriber<NewArrivageCreated>() {
            @Override
            public void handleEvent(NewArrivageCreated aDomainEvent) {
                System.out.println(aDomainEvent.toString());
            }

            @Override
            public Class<NewArrivageCreated> subscribedToEventType() {
                return NewArrivageCreated.class;
            }
        };

        DomainEventSubscriber<ProductCreated> domainEventSubscriber2 = new DomainEventSubscriber<ProductCreated>() {
            @Override
            public void handleEvent(ProductCreated aDomainEvent) {
                System.out.println(aDomainEvent.toString());
            }
            @Override
            public Class<ProductCreated> subscribedToEventType() {
                return ProductCreated.class;
            }
        };



        DomainEventPublisher.instance().subscribe(domainEventSubscriber1);
        DomainEventPublisher.instance().subscribe(domainEventSubscriber2);
        //TO BE REFACTORED



		Product product = 
				new Product(
						new ProductId("P12345"),
                        new StockId("STOCK_ID_1"),
						"My product name", 
						"Nails for soft walls");




        Arrivage  arrivage =
                product.createNewArrivage(
                        new ArrivageId("ARR12345"),
                        new Quantity(10),
                        new BigDecimal(5.5),"Ciment Dangote");
		
		assertEquals(product.productId(), arrivage.productId());


        assertEquals(2, handledEvents.size());
        assertEquals(handledEvents.get(0), ProductCreated.class);
        assertEquals(handledEvents.get(1), NewArrivageCreated.class);

		
	}
	
	@Test 
	public void createStock(){

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

        DomainEventSubscriber<ProductCreated> domainEventSubscriber1 = new DomainEventSubscriber<ProductCreated>() {
            @Override
            public void handleEvent(ProductCreated aDomainEvent) {
                System.out.println(aDomainEvent.toString());
            }
            @Override
            public Class<ProductCreated> subscribedToEventType() {
                return ProductCreated.class;
            }
        };

        DomainEventSubscriber<ProductStockCreated> domainEventSubscriber2 = new DomainEventSubscriber<ProductStockCreated>() {
            @Override
            public void handleEvent(ProductStockCreated aDomainEvent) {
                System.out.println(aDomainEvent.toString());
            }

            @Override
            public Class<ProductStockCreated> subscribedToEventType() {
                return ProductStockCreated.class;
            }
        };




        DomainEventPublisher.instance().subscribe(domainEventSubscriber1);
        DomainEventPublisher.instance().subscribe(domainEventSubscriber2);
        //TO BE REFACTORED



        Product product =
				new Product(
						new ProductId("P12345"),
						"My product name", 
						"Nails for soft walls");
		
		Stock stockForProduct = product.createStock(new StockId("ST12345"));
		
		assertEquals(stockForProduct.productId(), product.productId());
		assertEquals(stockForProduct.stockId(), product.stockId());

        assertEquals(2, handledEvents.size());
        assertEquals(handledEvents.get(0), ProductCreated.class);
        assertEquals(handledEvents.get(1), ProductStockCreated.class);



    }
}

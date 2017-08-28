package com.dddtraining.inventory.port.adpter.persistence;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ArrivageRepositoryTest {
	
	@Autowired
	private ArrivageRepository arrivageRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StockRepository stockRepository;

    @Test
    public void testAddArrivage() {
    	
    	
    	ProductId productId = this.productRepository.nextIdentity();
    	ArrivageId arrivageId = this.arrivageRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();


        Arrivage arrivage =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");

        this.arrivageRepository.add(arrivage);

        assertEquals(1, arrivageRepository.allArrivages().size());

        Arrivage foundArrivage = arrivageRepository.arrivgeOfId(arrivage.arrivageId());

        assertEquals(arrivage, foundArrivage);

    }

    @Test
    public void testRemoveArrivage() {


    	ProductId productId = this.productRepository.nextIdentity();
    	ArrivageId arrivageId = this.arrivageRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();


        Arrivage arrivage =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");

        this.arrivageRepository.add(arrivage);

        assertEquals(1, arrivageRepository.allArrivages().size());

        this.arrivageRepository.remove(arrivage);

        assertEquals(0, arrivageRepository.allArrivages().size());

    }

    @Test
    public void testArrivageOfId() {

    	ProductId productId = this.productRepository.nextIdentity();
    	ArrivageId arrivageId = this.arrivageRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();


        Arrivage arrivage =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");

        this.arrivageRepository.add(arrivage);

        assertEquals(1, arrivageRepository.allArrivages().size());

        Arrivage foundArrivage = arrivageRepository.arrivgeOfId(arrivage.arrivageId());

        assertEquals(arrivage, foundArrivage);
    }

    @Test
    public void testAllArrivagesPriorTo() {
    	
    	
    	ProductId productId = this.productRepository.nextIdentity();
    	ArrivageId arrivageId1 = this.arrivageRepository.nextIdentity();
    	ArrivageId arrivageId2 = this.arrivageRepository.nextIdentity();
    	ArrivageId arrivageId3 = this.arrivageRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();
    	
        Arrivage arrivage1 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId1,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");
        
        
        try {
			Thread.sleep(2000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        Arrivage arrivage2 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId2,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");
        try {
			Thread.sleep(2000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        
        Arrivage arrivage3 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId3,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");

        this.arrivageRepository.add(arrivage1);
        this.arrivageRepository.add(arrivage2);
        this.arrivageRepository.add(arrivage3);


     
        Collection<Arrivage> allArrivagesPriorToArrivage2Creation = 
        		arrivageRepository
        		.allArrivagesPriorTo(arrivage2.lifeSpanTime().startDate().minusNanos(1));

        assertEquals(1, allArrivagesPriorToArrivage2Creation.size());
    }

    @Test
    public void testAllArrivagesAfter() {



    	ProductId productId = this.productRepository.nextIdentity();
    	ArrivageId arrivageId1 = this.arrivageRepository.nextIdentity();
    	ArrivageId arrivageId2 = this.arrivageRepository.nextIdentity();
    	ArrivageId arrivageId3 = this.arrivageRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();
    	
        Arrivage arrivage1 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId1,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");
        
        
        try {
			Thread.sleep(2000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        Arrivage arrivage2 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId2,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");
        try {
			Thread.sleep(2000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        
        Arrivage arrivage3 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId3,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");

        this.arrivageRepository.add(arrivage1);
        this.arrivageRepository.add(arrivage2);
        this.arrivageRepository.add(arrivage3);


     
        Collection<Arrivage> allArrivagesAfterArrivage2Creation = 
        		arrivageRepository
        		.allArrivagesAfter(arrivage2.lifeSpanTime().startDate().plusNanos(1));

        assertEquals(1, allArrivagesAfterArrivage2Creation.size());
    }

    @Test
    public void testAllArrivagesBetweenDates() {
    	
    	
    	ProductId productId = this.productRepository.nextIdentity();
    	ArrivageId arrivageId1 = this.arrivageRepository.nextIdentity();
    	ArrivageId arrivageId2 = this.arrivageRepository.nextIdentity();
    	ArrivageId arrivageId3 = this.arrivageRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();
    	
        Arrivage arrivage1 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId1,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");
        
        

        
        Arrivage arrivage2 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId2,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");
   

        
        Arrivage arrivage3 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId3,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");

        this.arrivageRepository.add(arrivage1);
        this.arrivageRepository.add(arrivage2);
        this.arrivageRepository.add(arrivage3);




        Collection<Arrivage> allArrivagesBetweenYesterdayAndNow = arrivageRepository.allArrivagesBetweenDates(ZonedDateTime.now().minusDays(1l), ZonedDateTime.now());

        assertEquals(3, allArrivagesBetweenYesterdayAndNow.size());
    }


    @Test
    public void testAllArrivageOfProductId() {


    	ProductId productId1 = this.productRepository.nextIdentity();
    	ProductId productId2 = this.productRepository.nextIdentity();
    	ArrivageId arrivageId1 = this.arrivageRepository.nextIdentity();
    	ArrivageId arrivageId2 = this.arrivageRepository.nextIdentity();
    	ArrivageId arrivageId3 = this.arrivageRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();
    	
        Arrivage arrivage1 =
                new Arrivage(
                		productId1,
                		stockId,
                		arrivageId1,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");
        
        

        
        Arrivage arrivage2 =
                new Arrivage(
                		productId2,
                		stockId,
                		arrivageId2,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");
   

        
        Arrivage arrivage3 =
                new Arrivage(
                		productId2,
                		stockId,
                		arrivageId3,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");

        this.arrivageRepository.add(arrivage1);
        this.arrivageRepository.add(arrivage2);
        this.arrivageRepository.add(arrivage3);


        Collection<Arrivage> arrivages = arrivageRepository.allArrivagesOfProductId(productId2);


        assertNotNull(arrivages);

        assertEquals(2, arrivages.size());
    }

    @Test
    public void testAllArrivages() {

    	ProductId productId = this.productRepository.nextIdentity();
    	ArrivageId arrivageId1 = this.arrivageRepository.nextIdentity();
    	ArrivageId arrivageId2 = this.arrivageRepository.nextIdentity();
    	ArrivageId arrivageId3 = this.arrivageRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();
    	
        Arrivage arrivage1 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId1,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");
        
        

        
        Arrivage arrivage2 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId2,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");
   

        
        Arrivage arrivage3 =
                new Arrivage(
                		productId,
                		stockId,
                		arrivageId3,
                        new Quantity(477),
                        new BigDecimal(540),
                        "ARRIVAGE DESCRIPTION");

        this.arrivageRepository.add(arrivage1);
        this.arrivageRepository.add(arrivage2);
        this.arrivageRepository.add(arrivage3);
        
        Collection<Arrivage> arrivages = arrivageRepository.allArrivages();

        assertEquals(3, arrivages.size());


    }


}

package com.dddtraining.inventory.port.adpter.persistence;


import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.port.adapter.persistence.MockArrivageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArrivageRepositoryTest {

    @Test
    public void testAddArrivage() {

        ArrivageRepository arrivageRepository = new MockArrivageRepository();

        assertEquals(5, arrivageRepository.allArrivages().size());

        Arrivage arrivage =
                new Arrivage(
                        new ProductId("PR12345"),
                        new StockId("STOCK_ID_2"),
                        new ArrivageId("ARR12350"),
                        new Quantity(477),
                        new BigDecimal(540),
                        "Arrivage de la periode des fetes de fin d'annee");

        arrivageRepository.add(arrivage);

        assertEquals(6, arrivageRepository.allArrivages().size());

        Arrivage foundArrivage = arrivageRepository.arrivgeOfId(new ArrivageId("ARR12350"));

        assertEquals(arrivage, foundArrivage);

    }

    @Test
    public void testRemoveArrivage() {


        ArrivageRepository arrivageRepository = new MockArrivageRepository();

        assertEquals(5, arrivageRepository.allArrivages().size());

        Arrivage arrivage =
                new Arrivage(
                        new ProductId("PR12345"),
                        new StockId("STOCK_ID_2"),
                        new ArrivageId("ARR12345"),
                        new Quantity(1000),
                        new BigDecimal(500),
                        "Arrivage de la periode des fetes");


        arrivageRepository.remove(arrivage);

        assertEquals(4, arrivageRepository.allArrivages().size());

        Arrivage foundArrivage = arrivageRepository.arrivgeOfId(arrivage.arrivageId());

        assertNull(foundArrivage);


    }

    @Test
    public void testArrivageOfId() {

        ArrivageRepository arrivageRepository = new MockArrivageRepository();

        Arrivage arrivage =
                new Arrivage(
                        new ProductId("PR12345"),
                        new StockId("STOCK_ID_2"),
                        new ArrivageId("ARR12350"),
                        new Quantity(1000),
                        new BigDecimal(500),
                        "Arrivage de la periode des fetes");

        arrivageRepository.add(arrivage);

        Arrivage foundArrivage = arrivageRepository.arrivgeOfId(new ArrivageId("ARR12350"));

        assertNotNull(foundArrivage);

        assertEquals(arrivage, foundArrivage);

    }

    @Test
    public void testAllArrivagesPriorTo() {

        ArrivageRepository arrivageRepository = new MockArrivageRepository();

        Set<Arrivage> allArrivagesPriorToNow = arrivageRepository.allArrivagesPriorTo(ZonedDateTime.now().plusDays(1l));

        assertEquals(5, allArrivagesPriorToNow.size());
    }

    @Test
    public void testAllArrivagesAfter() {


        ArrivageRepository arrivageRepository = new MockArrivageRepository();

        Set<Arrivage> allArrivagesPriorToNow = arrivageRepository.allArrivagesAfter(ZonedDateTime.now().minusDays(1l));

        assertEquals(5, allArrivagesPriorToNow.size());
    }

    @Test
    public void testAllArrivagesBetweenDates() {


        ArrivageRepository arrivageRepository = new MockArrivageRepository();

        Set<Arrivage> allArrivagesPriorToNow = arrivageRepository.allArrivagesBetweenDates(ZonedDateTime.now().minusDays(1l), ZonedDateTime.now().plusDays(1l));

        assertEquals(5, allArrivagesPriorToNow.size());
    }


    @Test
    public void testAllArrivageOfProductId() {

        ArrivageRepository arrivageRepository = new MockArrivageRepository();

        assertEquals(5, arrivageRepository.allArrivages().size());


        Set<Arrivage> arrivages = arrivageRepository.allArrivagesOfProductId(new ProductId("PR12345"));


        assertNotNull(arrivages);

        assertEquals(2, arrivages.size());
    }

    @Test
    public void testAllArrivages() {

        ArrivageRepository arrivageRepository = new MockArrivageRepository();

        assertEquals(5, arrivageRepository.allArrivages().size());

        Set<Arrivage> arrivages = arrivageRepository.allArrivages();

        assertEquals(5, arrivages.size());


    }


}

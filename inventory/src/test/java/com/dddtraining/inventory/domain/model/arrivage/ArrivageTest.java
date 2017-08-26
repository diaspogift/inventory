package com.dddtraining.inventory.domain.model.arrivage;

import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageQuantityChanged;
import com.dddtraining.inventory.domain.model.common.EventTrackingTest;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.StockId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArrivageTest extends EventTrackingTest{


    @Test
    public void testCreateArrivage() {

        Arrivage arrivage =
                new Arrivage(
                        new ProductId("PR12345"),
                        new StockId("STOCK_ID_2"),
                        new ArrivageId("ARR12345"),
                        new Quantity(0),
                        new BigDecimal(500),
                        "Arrivage de la periode des fetes");

        assertNotNull(arrivage);

        assertEquals(new ProductId("PR12345"), arrivage.productId());
        assertEquals(new ArrivageId("ARR12345"), arrivage.arrivageId());
        assertEquals(new Quantity(0), arrivage.quantity());
        assertEquals(new BigDecimal(500), arrivage.uniPrice());
        assertEquals("Arrivage de la periode des fetes", arrivage.description());


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

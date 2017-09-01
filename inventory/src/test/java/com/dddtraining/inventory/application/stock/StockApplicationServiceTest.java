package com.dddtraining.inventory.application.stock;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.dddtraining.inventory.application.ApplicationServiceCommonTest;
import com.dddtraining.inventory.application.ApplicationServiceRegistry;
import com.dddtraining.inventory.domain.model.DomainRegistry;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.stock.StockId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.application.command.CreateStockCommand;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockApplicationServiceTest extends ApplicationServiceCommonTest{


    @Test
    public void testAddStock() {

        Product product1 = this.product1;
        Stock stockForProduct1 = this.stockProduct1;

        this.entityManager().getTransaction().begin();
        this.entityManager().persist(this.product1);
        this.entityManager().getTransaction().commit();



        ApplicationServiceRegistry.stockApplicationService()
                .createStock(
                        new CreateStockCommand(
                        stockForProduct1.stockId().id(),
                        product1.productId().id(),
                        1000
                ));


        Stock stock =
                DomainRegistry.stockRepository().stockOfId(stockForProduct1.stockId());

        assertNotNull(stock);
        assertEquals(new Quantity(1000), stock.quantity());


        this.entityManager().getTransaction().begin();
        this.entityManager().createQuery("delete from Product").executeUpdate();
        this.entityManager().createQuery("delete from Stock").executeUpdate();
        this.entityManager().getTransaction().commit();


    }
}

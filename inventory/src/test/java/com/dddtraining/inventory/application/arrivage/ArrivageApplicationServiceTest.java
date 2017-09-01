package com.dddtraining.inventory.application.arrivage;

import static org.junit.Assert.assertEquals;

import com.dddtraining.inventory.application.ApplicationServiceCommonTest;
import com.dddtraining.inventory.application.ApplicationServiceRegistry;
import com.dddtraining.inventory.domain.model.DomainRegistry;
import com.dddtraining.inventory.domain.model.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.application.command.ChangeArrivageQuantityCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ArrivageApplicationServiceTest extends ApplicationServiceCommonTest {



	@Test
    public void testChangeArrivageQuantity() {



		this.stockProduct1().addNewStockProductArrivage(this.arrivage1());
		this.stockProduct1().addNewStockProductArrivage(this.arrivage2());


		this.entityManager().getTransaction().begin();
		this.entityManager().persist(this.product1());
		this.entityManager().persist(this.arrivage1());
		this.entityManager().persist(this.arrivage2());
		this.entityManager().persist(this.stockProduct1());
		this.entityManager().getTransaction().commit();



		ApplicationServiceRegistry.arrivageApplicationService()
    	.changeArrivageQuantity(
    			new ChangeArrivageQuantityCommand(
    					arrivageId1().id(),
    					200));

    	
    
         Stock modifiedStock = 
         		DomainRegistry.stockRepository().stockOfId(this.stockProduct1().stockId());
         Arrivage modifiedArrivage1 =
				 DomainRegistry.arrivageRepository().arrivgeOfId(this.arrivageId1());
         Arrivage unmodifiedArrivage2 =
				 DomainRegistry.arrivageRepository().arrivgeOfId(this.arrivageId2());
    	
     	
         
		assertEquals(new Quantity(200), modifiedArrivage1.quantity());
		assertEquals(new Quantity(300), unmodifiedArrivage2.quantity());
		assertEquals(2, modifiedStock.stockProductArrivages().size());



		this.entityManager().getTransaction().begin();
		this.entityManager().createQuery("delete from Product").executeUpdate();
		this.entityManager().createQuery("delete from Arrivage").executeUpdate();
		this.entityManager().createQuery("delete from Stock").executeUpdate();
		this.entityManager().createQuery("delete from StockProductArrivage").executeUpdate();
		this.entityManager().getTransaction().commit();
    	

    }




    

}
package com.dddtraining.inventory.application.product;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import com.dddtraining.inventory.application.ApplicationServiceCommonTest;
import com.dddtraining.inventory.application.ApplicationServiceRegistry;
import com.dddtraining.inventory.domain.model.DomainRegistry;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.application.command.CreateProductStockCommand;
import com.dddtraining.inventory.application.command.DecrementProductStockCommand;
import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.application.command.RegisterProductCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationServiceTest extends ApplicationServiceCommonTest {


    @Test
    public void testAddNewProduct() {


    	Product product1 =  this.product1();


		ApplicationServiceRegistry.productApplicationService()
				.addProduct(new RegisterProductCommand(
						product1.productId().id(),
						product1.name(),
						product1.description()));


		Product addedProduct = DomainRegistry.productRepository().productOfId(product1.productId());

        assertNotNull(addedProduct);
        assertEquals(product1, addedProduct);

		this.entityManager().getTransaction().begin();
		this.entityManager().createQuery("delete from Product").executeUpdate();
		this.entityManager().getTransaction().commit();


    }

    @Test
    public void testAddProductArrivage() {

		Product product = this.product1();
		Stock stock = this.stockProduct1();
		Arrivage arrivage1 = this.arrivage1();



		this.entityManager().getTransaction().begin();
		this.entityManager().persist(product);
		this.entityManager().persist(stock);
		this.entityManager().getTransaction().commit();



		ApplicationServiceRegistry.productApplicationService()
				.addProductArrivage(
						new RegisterProductArrivageCommand(
								arrivage1.productId().id(),
								arrivage1.arrivageId().id(),
								arrivage1.quantity().value(),
								arrivage1.unitPrice(),
								"ARRIVAGE 1 DESCRPTION"
						));


        Arrivage addedArrivage1 = 
        		DomainRegistry.arrivageRepository().arrivgeOfId(arrivage1.arrivageId());


		assertNotNull(addedArrivage1);
		assertEquals(arrivage1, addedArrivage1);



		this.entityManager().getTransaction().begin();
		this.entityManager().createQuery("delete from Product").executeUpdate();
		this.entityManager().createQuery("delete from Arrivage").executeUpdate();
		this.entityManager().createQuery("delete from Stock").executeUpdate();
		this.entityManager().createQuery("delete from StockProductArrivage").executeUpdate();
		this.entityManager().getTransaction().commit();



	}

    @Test
    public void testCreatProductStock() {


		this.entityManager().getTransaction().begin();
		this.entityManager().persist(this.product1);
		this.entityManager().getTransaction().commit();


		ApplicationServiceRegistry.productApplicationService()
				.creatProductStock(
						new CreateProductStockCommand(
								this.stockId1.id(),
								this.productId1.id(),
								1000));



		Stock stock = DomainRegistry.stockRepository().stockOfId(stockId1);


		assertNotNull(stock);
		assertEquals(stock.stockId(), stockId1);
		assertEquals(stock.productId(), this.product1.productId());


		this.entityManager().getTransaction().begin();
		this.entityManager().createQuery("delete from Product").executeUpdate();
		this.entityManager().createQuery("delete from Stock").executeUpdate();
		this.entityManager().getTransaction().commit();

    }


    //TO DO

    @Test
    public void testDecrementProductStock() {


		this.stockProduct1.addNewStockProductArrivage(this.arrivage1);
		this.stockProduct1.addNewStockProductArrivage(this.arrivage2);

		this.entityManager().getTransaction().begin();
		this.entityManager().persist(this.product1());
		this.entityManager().persist(this.arrivage1());
		this.entityManager().persist(this.arrivage2());
		this.entityManager().persist(this.stockProduct1());
		this.entityManager().getTransaction().commit();


        ApplicationServiceRegistry.productApplicationService()
                .decrementProductStock(
                		 new DecrementProductStockCommand(
                                 350,
                                  productId1.id()));


        Stock modifiedStock = 
        		DomainRegistry.stockRepository().stockOfId(stockProduct1.stockId());

        
        
        assertEquals(200, modifiedStock.quantity().value());


		this.entityManager().getTransaction().begin();
		this.entityManager().createQuery("delete from Product").executeUpdate();
		this.entityManager().createQuery("delete from Arrivage").executeUpdate();
		this.entityManager().createQuery("delete from Stock").executeUpdate();
		this.entityManager().createQuery("delete from StockProductArrivage").executeUpdate();
		this.entityManager().getTransaction().commit();
       



    }



}

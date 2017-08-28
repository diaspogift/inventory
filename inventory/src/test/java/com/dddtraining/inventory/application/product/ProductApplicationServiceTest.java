package com.dddtraining.inventory.application.product;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.application.command.CreateProductStockCommand;
import com.dddtraining.inventory.application.command.DecrementProductStockCommand;
import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.application.command.RegisterProductCommand;
import com.dddtraining.inventory.application.stock.StockApplicationService;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationServiceTest {


    @Autowired
    private ProductApplicationService productApplicationService;
    @Autowired
    private StockApplicationService stockApplicationService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArrivageRepository arrivageRepository;
    @Autowired
    private StockRepository stockRepository;


    @Test
    public void testAddNewProduct() {


        RegisterProductCommand registerProductCommand =
                new RegisterProductCommand(
                        "PROD_OF_ID_1",
                        "Product name",
                        "Product desciption"
                );

        productApplicationService.addProduct(registerProductCommand);

        Product newProduct =
                this.productRepository
                        .productOfId(
                                new ProductId("PROD_OF_ID_1"));

        assertNotNull(newProduct);
        assertEquals("Product name", newProduct.name());
        assertEquals("Product desciption", newProduct.description());


    }

    @Test
    public void testAddProductArrivage() {


    	ProductId productId = this.productRepository().nextIdentity();
    	StockId stockId = this.stockRepository().nextIdentity();
    	ArrivageId arrivageId1 = this.arrivageRepository().nextIdentity();
    	ArrivageId arrivageId2 = this.arrivageRepository().nextIdentity();
    	
    	RegisterProductCommand registerProductCommand = new RegisterProductCommand(productId.id(), "PRODUCT NAME", "PRODUCT DESCRIPTION");
    
    	
    	this.productApplicationService().addProduct(registerProductCommand);
    
    	this.productApplicationService().creatProductStock(new CreateProductStockCommand(stockId.id(), productId.id(), 100));
    	
    	this.productApplicationService()
    	.addProductArrivage(
    			new RegisterProductArrivageCommand(
    					productId.id(), arrivageId1.id(),
    					250,
    					new BigDecimal(150),
    					"ARRIVAGE DESCRPTION 1"));
    	
    	try {
			Thread.sleep(2000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	
    	
    	this.productApplicationService()
    	.addProductArrivage(
    			new RegisterProductArrivageCommand(
    					productId.id(), arrivageId2.id(),
    					300,
    					new BigDecimal(250),
    					"ARRIVAGE DESCRPTION 2"));
    	
      	try {
    			Thread.sleep(2000l);
    	} catch (InterruptedException e) {
    			e.printStackTrace();
    	}
      	
        Stock modifiedStock = 
        		this.stockRepository().stockOfId(stockId);
        Arrivage addedArrivage1 = 
        		this.arrivageRepository().arrivgeOfId(arrivageId1);
        Arrivage addedArrivage2 = 
        		this.arrivageRepository().arrivgeOfId(arrivageId2);
        
        assertEquals(550, modifiedStock.quantity().value());
        assertEquals(250, addedArrivage1.quantity().value());
        assertEquals(300, addedArrivage2.quantity().value());
        
        
        
        
    }

    @Test
    public void testCreatProductStock() {
    	
    	
    	ProductId productId = this.productRepository().nextIdentity();
    	StockId stockId = this.stockRepository().nextIdentity();
    	
    	RegisterProductCommand registerProductCommand = new RegisterProductCommand(productId.id(), "PRODUCT NAME", "PRODUCT DESCRIPTION");
    
    	
    	this.productApplicationService().addProduct(registerProductCommand);
    
    	this.productApplicationService().creatProductStock(new CreateProductStockCommand(stockId.id(), productId.id(), 100));
    	
    	
    	try {
			Thread.sleep(2000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	Product product = 
    			this.productApplicationService()
    			.product(productId.id());
    	
    	Stock stock = 
    			this.stockApplicationService.stock(stockId.id());
    	
    	assertEquals(stock.stockId(), product.stockId());




    }


    //TO DO

    @Test
    public void testDecrementProductStock() {
    	
    	
    	ProductId productId = this.productRepository().nextIdentity();
    	StockId stockId = this.stockRepository().nextIdentity();
    	ArrivageId arrivageId1 = this.arrivageRepository().nextIdentity();
    	ArrivageId arrivageId2 = this.arrivageRepository().nextIdentity();
    	
    	RegisterProductCommand registerProductCommand = new RegisterProductCommand(productId.id(), "PRODUCT NAME", "PRODUCT DESCRIPTION");
    
    	
    	this.productApplicationService().addProduct(registerProductCommand);
    
    	this.productApplicationService().creatProductStock(new CreateProductStockCommand(stockId.id(), productId.id(), 100));
    	
    	this.productApplicationService()
    	.addProductArrivage(
    			new RegisterProductArrivageCommand(
    					productId.id(), arrivageId1.id(),
    					250,
    					new BigDecimal(150),
    					"ARRIVAGE DESCRPTION 1"));
    	
    	try {
			Thread.sleep(2000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	
    	
    	this.productApplicationService()
    	.addProductArrivage(
    			new RegisterProductArrivageCommand(
    					productId.id(), arrivageId2.id(),
    					300,
    					new BigDecimal(250),
    					"ARRIVAGE DESCRPTION 2"));
    	
      	try {
    			Thread.sleep(2000l);
    	} catch (InterruptedException e) {
    			e.printStackTrace();
    	}
    	
    	
      	
      	
        this.productApplicationService
                .decrementProductStock(
                		 new DecrementProductStockCommand(
                                 350,
                                 productId.id()));
        
        
        
      	try {
    			Thread.sleep(5000l);
    	} catch (InterruptedException e) {
    			e.printStackTrace();
    	}
        
        Stock modifiedStock = 
        		this.stockRepository().stockOfId(stockId);
        Arrivage modifiedArrivage1 = 
        		this.arrivageRepository().arrivgeOfId(arrivageId1);
        Arrivage modifiedArrivage2 = 
        		this.arrivageRepository().arrivgeOfId(arrivageId2);
        
        
        assertEquals(200, modifiedStock.quantity().value());
        assertEquals(0, modifiedArrivage1.quantity().value());
        assertEquals(200, modifiedArrivage2.quantity().value());


        
       



    }

	public ProductApplicationService productApplicationService() {
		return productApplicationService;
	}

	public ProductRepository productRepository() {
		return productRepository;
	}

	public ArrivageRepository arrivageRepository() {
		return arrivageRepository;
	}

	public StockRepository stockRepository() {
		return stockRepository;
	}


}

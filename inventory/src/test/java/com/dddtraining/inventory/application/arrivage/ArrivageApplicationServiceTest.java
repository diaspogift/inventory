package com.dddtraining.inventory.application.arrivage;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.application.command.ChangeArrivageQuantityCommand;
import com.dddtraining.inventory.application.command.CreateProductStockCommand;
import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.application.command.RegisterProductCommand;
import com.dddtraining.inventory.application.product.ProductApplicationService;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ArrivageApplicationServiceTest {

	
	@Autowired
	private  ArrivageApplicationService arrivageApplicationService;
	@Autowired
	private ProductApplicationService productApplicationService;
	@Autowired
	private ArrivageRepository arrivageRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StockRepository stockRepository;
	
	
	

    @Test
    public void testChangeArrivageQuantity() {
    	
    	

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
    	
    	
    	this.arrivageApplicationService()
    	.changeArrivageQuantity(
    			new ChangeArrivageQuantityCommand(
    					arrivageId1.id(),
    					200));
    	
      	try {
    			Thread.sleep(2000l);
    	} catch (InterruptedException e) {
    			e.printStackTrace();
    	}
    	
    	
    
         Stock modifiedStock = 
         		this.stockRepository().stockOfId(stockId);
         Arrivage modifiedArrivage1 = 
         		this.arrivageRepository().arrivgeOfId(arrivageId1);
         Arrivage unmodifiedArrivage2 = 
         		this.arrivageRepository().arrivgeOfId(arrivageId2);
    	
     	
         
         assertEquals(new Quantity(200), modifiedArrivage1.quantity());
         assertEquals(new Quantity(300), unmodifiedArrivage2.quantity());
         assertEquals(new Quantity(500), modifiedStock.quantity());
         
    	
    	

    }



	public ArrivageApplicationService arrivageApplicationService() {
		return this.arrivageApplicationService;
	}



	public ArrivageRepository arrivageRepository() {
		return this.arrivageRepository;
	}






	public ProductRepository productRepository() {
		return productRepository;
	}



	public StockRepository stockRepository() {
		return stockRepository;
	}



	public ProductApplicationService productApplicationService() {
		return productApplicationService;
	}

    

}
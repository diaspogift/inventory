package com.dddtraining.inventory.port.adpter.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StockRepositoryTest {
	
	@Autowired
	private ArrivageRepository arrivageRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StockRepository stockRepository;
	
	

    @Test
    public void testAddStock() {

    	
    	ProductId productId = this.productRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();

        Stock stock = new Stock(stockId, productId);

        this.stockRepository.add(stock);

        assertEquals(1, this.stockRepository.allStocks().size());

        Stock savedStock = this.stockRepository.stockOfId(stockId);

        assertEquals(stock, savedStock);

    }

    @Test
    public void testRemoveStock() {

    	ProductId productId = this.productRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();


        Stock stock = new Stock(stockId, productId);

        this.stockRepository.add(stock);

        assertEquals(1, this.stockRepository.allStocks().size());


        stockRepository.remove(stock);

        assertEquals(0, this.stockRepository.allStocks().size());

    }

    @Test
    public void testStockOfId() {

    	ProductId productId = this.productRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();

        Stock stock = new Stock(stockId, productId);

        this.stockRepository.add(stock);

        assertEquals(1, stockRepository.allStocks().size());

        Stock foundStock = stockRepository.stockOfId(stockId);

        assertEquals(stock, foundStock);

    }

   
    @Test
    public void testAllActiveStocks() {

    	ProductId productId = this.productRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();

        Stock stock1 = new Stock(stockId, productId);
        Stock stock2 = new Stock(stockId, productId);
        Stock stock3 = new Stock(stockId, productId);

        this.stockRepository.add(stock1);
        this.stockRepository.add(stock2);
        this.stockRepository.add(stock3);
        
        
        

        Collection<Stock> acitiveStocks = this.stockRepository.allAvailableStock();
        assertEquals(0, acitiveStocks.size());

    }

    @Test
    public void testAllStocks() {


    	ProductId productId = this.productRepository.nextIdentity();
    	StockId stockId = this.stockRepository.nextIdentity();

        Stock stock1 = new Stock(stockId, productId);
        Stock stock2 = new Stock(stockId, productId);
        Stock stock3 = new Stock(stockId, productId);

        this.stockRepository.add(stock1);
        this.stockRepository.add(stock2);
        this.stockRepository.add(stock3);
        
        Collection<Stock> acitiveStocks = this.stockRepository.allStocks();
        assertEquals(3, acitiveStocks.size());
        
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    }

}

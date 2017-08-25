package com.dddtraining.inventory.port.adapter.persistence;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MockStockRepository implements StockRepository {
	
	
	
	private Set<Stock> allStocks;
	
	
	
	
	

	public MockStockRepository() {
		super();
		this.allStocks = new HashSet<Stock>();
		
		Stock stk1Prod1 = 
				new Stock(
						new StockId("STOCK_ID_1"),
						new ProductId("PRODUCT_ID_1"),
						new Quantity(200),
                        150);
		
		Stock stk2Prod2 =
				new Stock(new StockId("STOCK_ID_2"),
						new ProductId("PRODUCT_ID_2"),
                        new Quantity(200));
		
		Stock stk3Prod3 =
				new Stock(new StockId("STOCK_ID_3"),
						new ProductId("PRODUCT_ID_3"));
		
		Stock stk4Prod4 =
				new Stock(new StockId("STOCK_ID_4"),
						new ProductId("PRODUCT_ID_4"));
		
		this.allStocks.add(stk1Prod1);
		this.allStocks.add(stk2Prod2);
		this.allStocks.add(stk3Prod3);
		this.allStocks.add(stk4Prod4);
		
	
	}

	@Override
	public StockId nextIdentity() {
		return new StockId(UUID.randomUUID().toString().toUpperCase());
	}

	public void add(Stock aStock) {
		this.allStocks.add(aStock);
	}

	public void remove(Stock aStock) {
		if(this.allStocks.contains(aStock)){
			this.allStocks.remove(aStock);
		}
	}

	
	public Stock stockOfId(StockId aStockId) {
		
		Stock result = null;
		
		for(Stock nextStock : allStocks){
			if(nextStock.stockId().equals(aStockId)){
				result = (nextStock);
			}
		}
		return result;
	}

	public Stock stockForProductOfId(ProductId aProductId) {

        Stock result = null;

        for(Stock nextStock : allStocks){
            if(nextStock.productId().equals(aProductId)){
                result = nextStock;
            }
        }
        return result;
	}



	public Set<Stock> allAvailableStock() {
		
		Set<Stock> result = new HashSet<Stock>();

		for(Stock nextStock : allStocks){
			if(nextStock.isAvailable()){
				result.add(nextStock);
			}
		}
		
		
		return result;
	}

	public Set<Stock> allStocks() {
		return this.allStocks;
	}



}

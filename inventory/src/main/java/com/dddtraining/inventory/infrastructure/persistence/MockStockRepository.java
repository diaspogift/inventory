package com.dddtraining.inventory.infrastructure.persistence;

import java.util.HashSet;
import java.util.Set;

import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockRepository;

public class MockStockRepository implements StockRepository {
	
	
	
	private Set<Stock> allStocks;
	
	
	
	
	

	public MockStockRepository() {
		super();
		this.allStocks = new HashSet<Stock>();
		
		Stock stk1Prod1 = 
				new Stock(new StockId("STOCK_ID_1"),
						new ProductId("PRODUCT_ID_1"));
		
		Stock stk2Prod1 = 
				new Stock(new StockId("STOCK_ID_2"),
						new ProductId("PRODUCT_ID_1"));
		
		Stock stk3Prod2 = 
				new Stock(new StockId("STOCK_ID_3"),
						new ProductId("PRODUCT_ID_2"));
		
		Stock stk4Prod3 = 
				new Stock(new StockId("STOCK_ID_4"),
						new ProductId("PRODUCT_ID_3"));
		
		this.allStocks.add(stk1Prod1);
		this.allStocks.add(stk2Prod1);
		this.allStocks.add(stk3Prod2);
		this.allStocks.add(stk4Prod3);
		
	
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
	public Set<Stock> allStockForProductOfId(ProductId aProductId) {
		
		Set<Stock> result = new HashSet<Stock>();
		
		for(Stock nextStock : allStocks){
			if(nextStock.productId().equals(aProductId)){
				result.add(nextStock);
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

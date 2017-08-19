package com.dddtraining.inventory.domain.model.stock;

import java.util.Set;

import com.dddtraining.inventory.domain.model.product.ProductId;


public interface StockRepository {
	
	public void  add(Stock aStock);
	public void remove(Stock aStock);
	public Stock stockOfId(StockId aStockId);
	public Set<Stock> allStockForProductOfId(ProductId aProductId);
	public Set<Stock> allAvailableStock();
	public Set<Stock> allStocks();


	

}

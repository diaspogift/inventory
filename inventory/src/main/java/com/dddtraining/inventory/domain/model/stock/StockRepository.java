package com.dddtraining.inventory.domain.model.stock;

import java.util.Collection;

import com.dddtraining.inventory.domain.model.product.ProductId;


public interface StockRepository {

    public StockId nextIdentity();

    public void add(Stock aStock);

    public void remove(Stock aStock);

    public Stock stockOfId(StockId aStockId);

    public Stock stockForProductOfId(ProductId aProductId);

    public Collection<Stock> allAvailableStock();

    public Collection<Stock> allStocks();


}

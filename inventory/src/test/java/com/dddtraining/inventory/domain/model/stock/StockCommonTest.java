package com.dddtraining.inventory.domain.model.stock;

import java.math.BigDecimal;

import com.dddtraining.inventory.DomainTest;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.product.ProductId;

public class StockCommonTest extends DomainTest{
	
	

	
	public StockCommonTest() {
		super();
	}

	public Stock StockWithThreeProductArrivages(){
		
		 Stock stock =
		            new Stock(
		                    new StockId("STOCK_ID_1"),
		                    new ProductId("PROD_ID_1")
		            );

		    Arrivage arrivage1 =
		            new Arrivage(
		                    new ProductId("PROD_ID_1"),
		                    new StockId("STOCK_ID_1"),
		                    new ArrivageId("ARR_ID_1"),
		                    new Quantity(300),
		                    new BigDecimal(105),
		                    "Des"
		            );

		    Arrivage arrivage2 =
		            new Arrivage(
		                    new ProductId("PROD_ID_1"),
		                    new StockId("STOCK_ID_1"),
		                    new ArrivageId("ARR_ID_2"),
		                    new Quantity(150),
		                    new BigDecimal(110),
		                    "Des"
		            );
		    Arrivage arrivage3 =
		            new Arrivage(
		                    new ProductId("PROD_ID_1"),
		                    new StockId("STOCK_ID_1"),
		                    new ArrivageId("ARR_ID_3"),
		                    new Quantity(100),
		                    new BigDecimal(85),
		                    "Des"
		            );

		    stock.addNewStockProductArrivage(arrivage1);
		    stock.addNewStockProductArrivage(arrivage2);
		    stock.addNewStockProductArrivage(arrivage3);
			return stock;
	}
	
   

}

package com.dddtraining.inventory.port.adpter.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import com.dddtraining.inventory.port.adapter.persistence.MockStockRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockRepositoryTest {

    @Test
    public void testAddStock() {

        StockRepository stockRepository = new MockStockRepository();

        assertEquals(4, stockRepository.allStocks().size());

        Stock stk5Prod3 =
                new Stock(
                        new StockId("STOCK_ID_5"),
                        new ProductId("PRODUCT_ID_3"));

        stockRepository.add(stk5Prod3);

        assertEquals(5, stockRepository.allStocks().size());

        Stock savedStock = stockRepository.stockOfId(new StockId("STOCK_ID_5"));

        assertEquals(stk5Prod3, savedStock);

    }

    @Test
    public void testRemoveStock() {

        StockRepository stockRepository = new MockStockRepository();

        assertEquals(4, stockRepository.allStocks().size());

        Stock stk1Prod1 = stockRepository.stockOfId(new StockId("STOCK_ID_1"));

        stockRepository.remove(stk1Prod1);

        assertEquals(3, stockRepository.allStocks().size());

    }

    @Test
    public void testStockOfId() {

        Stock ExpectedStk1Prod1 =
                new Stock(
                        new StockId("STOCK_ID_1"),
                        new ProductId("PRODUCT_ID_1"));

        StockRepository stockRepository = new MockStockRepository();

        Stock ActualStk1Prod1 = stockRepository.stockOfId(new StockId("STOCK_ID_1"));

        assertEquals(ExpectedStk1Prod1, ActualStk1Prod1);

    }

    @Test
    public void testAllStockForProductOfId() {

        StockRepository stockRepository = new MockStockRepository();

        Stock product1Stocks = stockRepository.stockForProductOfId(new ProductId("PRODUCT_ID_1"));
        Stock product2Stocks = stockRepository.stockForProductOfId(new ProductId("PRODUCT_ID_2"));
        Stock product3Stocks = stockRepository.stockForProductOfId(new ProductId("PRODUCT_ID_3"));

        assertEquals(new ProductId("PRODUCT_ID_1"), product1Stocks.productId());
        assertEquals(new ProductId("PRODUCT_ID_2"), product2Stocks.productId());
        assertEquals(new ProductId("PRODUCT_ID_3"), product3Stocks.productId());


    }

    @Test
    public void testAllActiveStocks() {

        StockRepository stockRepository = new MockStockRepository();

        Collection<Stock> acitiveStocks = stockRepository.allAvailableStock();
        assertEquals(4, acitiveStocks.size());

    }

    @Test
    public void testAllStocks() {

        StockRepository stockRepository = new MockStockRepository();

        Collection<Stock> allStocks = stockRepository.allStocks();
        assertEquals(4, allStocks.size());

    }

}

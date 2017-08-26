package com.dddtraining.inventory;

import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.common.EventTrackingTest;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import com.dddtraining.inventory.port.adapter.persistence.MockArrivageRepository;
import com.dddtraining.inventory.port.adapter.persistence.MockProductRepository;
import com.dddtraining.inventory.port.adapter.persistence.MockStockRepository;


public class DomainTest extends EventTrackingTest {


    protected ProductRepository productRepository;
    protected ArrivageRepository arrivageRepository;
    protected StockRepository stockRepository;

    public DomainTest() {
    }

    public void setUp() throws Exception {


        this.productRepository = new MockProductRepository();
        this.arrivageRepository = new MockArrivageRepository();
        this.stockRepository = new MockStockRepository();

        super.setUp();

    }

    public void tearDown() throws Exception {

        this.stockRepository = null;
        this.productRepository = null;
        this.arrivageRepository = null;
    }
}
package com.dddtraining.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.common.EventTrackingTest;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.StockRepository;


@Configurable
public class DomainTest extends EventTrackingTest {


	@Autowired
    protected ProductRepository productRepository;
	@Autowired
    protected ArrivageRepository arrivageRepository;
	@Autowired
    protected StockRepository stockRepository;

    public DomainTest() {
    }

    public void setUp() throws Exception {

    	super.setUp();
/*        this.productRepository = new JpaProductRepository();
        this.arrivageRepository = new JpaArrivageRepository();
        this.stockRepository = new JpaStockRepository();*/
    }

    public void tearDown() throws Exception {

        this.stockRepository = null;
        this.productRepository = null;
        this.arrivageRepository = null;
    }
}
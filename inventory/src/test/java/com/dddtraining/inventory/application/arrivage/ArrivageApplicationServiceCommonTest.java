package com.dddtraining.inventory.application.arrivage;


import com.dddtraining.inventory.domain.model.DomainRegistry;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;


public abstract class ArrivageApplicationServiceCommonTest{

    protected EntityManager entityManager;


    ProductId productId1 = new ProductId(UUID.randomUUID().toString().toUpperCase());
    StockId stockId1 = new StockId(UUID.randomUUID().toString().toUpperCase());
    Product product1 = new Product(productId1, stockId1, "PRODUCT NAME", "PRODUCT DESCRIPTION");


    ArrivageId arrivageId1 = new ArrivageId(UUID.randomUUID().toString().toUpperCase());
    ArrivageId arrivageId2 = new ArrivageId(UUID.randomUUID().toString().toUpperCase());

    Stock stockProduct1 = this.product1.createStock(stockId1, 1000);


    Arrivage arrivage1 = this.product1.createNewArrivage(arrivageId1, new Quantity(250), new BigDecimal(150),"ARRIVAGE DESCRPTION 1");
    Arrivage arrivage2 = this.product1.createNewArrivage(arrivageId2, new Quantity(300), new BigDecimal(250),"ARRIVAGE DESCRPTION 2");


    @Before
    public void setUp() throws Exception {
        DomainEventPublisher.instance().reset();



        this.entityManager = DomainRegistry.entityManagerFactory().createEntityManager();
        System.out.println("\n<<<<<<<<<<<<<<<<<<<< (setUp ArrivageApplicationServiceCommonTest)");

        this.createData();



    }


    @After
    public void tearDown() throws Exception {

        System.out.println("\n<<<<<<<<<<<<<<<<<<<< (tearDown ArrivageApplicationServiceCommonTest)");

        if(this.entityManager() != null){
            this.removeData();
            this.entityManager().close();
        }


    }


    private void createData(){


    }

    private void removeData(){



    }



    public ArrivageId arrivageId1() {
        return arrivageId1;
    }

    public ArrivageId arrivageId2() {
        return arrivageId2;
    }



    public Arrivage arrivage1() {
        return arrivage1;
    }

    public Arrivage arrivage2() {
        return arrivage2;
    }

    public ProductId productId1() {
        return productId1;
    }

    public StockId stockId1() {
        return stockId1;
    }

    public Product product1() {
        return product1;
    }

    public Stock stockProduct1() {
        return stockProduct1;
    }

    public ArrivageApplicationServiceCommonTest() {
    }
    public EntityManager entityManager() {
        return this.entityManager;
    }

}

package com.dddtraining.inventory.application;

import com.dddtraining.inventory.domain.model.DomainRegistry;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.junit.After;
import org.junit.Before;
import org.springframework.jms.config.JmsListenerConfigUtils;

import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;

public abstract class ApplicationServiceCommonTest {

    protected EntityManager entityManager;
    protected ProductId productId1;
    protected StockId stockId1;
    protected Product product1;
    protected ArrivageId arrivageId1;
    protected ArrivageId arrivageId2;
    protected Stock stockProduct1;
    protected Arrivage arrivage1;
    protected Arrivage arrivage2;


    @Before
    public void setUp() throws Exception {


        DomainEventPublisher.instance().reset();



        this.entityManager = DomainRegistry.entityManagerFactory().createEntityManager();
        System.out.println("\n<<<<<<<<<<<<<<<<<<<< (setUp ArrivageApplicationServiceCommonTest)");

         productId1 = new ProductId(UUID.randomUUID().toString().toUpperCase());
         stockId1 = new StockId(UUID.randomUUID().toString().toUpperCase());
         product1 = new Product(productId1, stockId1, "PRODUCT NAME", "PRODUCT DESCRIPTION");
        arrivageId1 = new ArrivageId(UUID.randomUUID().toString().toUpperCase());
        arrivageId2 = new ArrivageId(UUID.randomUUID().toString().toUpperCase());
        stockProduct1 = this.product1.createStock(stockId1, 1000);
        arrivage1 = this.product1.createNewArrivage(arrivageId1, new Quantity(250), new BigDecimal(150),"ARRIVAGE DESCRPTION 1");
        arrivage2 = this.product1.createNewArrivage(arrivageId2, new Quantity(300), new BigDecimal(250),"ARRIVAGE DESCRPTION 2");


    }


    @After
    public void tearDown() throws Exception {

        System.out.println("\n<<<<<<<<<<<<<<<<<<<< (tearDown ArrivageApplicationServiceCommonTest)");







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

    public ApplicationServiceCommonTest() {
    }
    public EntityManager entityManager() {
        return this.entityManager;
    }

}

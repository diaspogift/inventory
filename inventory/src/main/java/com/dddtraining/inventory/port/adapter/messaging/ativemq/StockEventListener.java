package com.dddtraining.inventory.port.adapter.messaging.ativemq;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

import com.dddtraining.inventory.InventoryApplication;
import com.dddtraining.inventory.application.command.AssignedStockCommand;
import com.dddtraining.inventory.application.product.ProductApplicationService;
import com.dddtraining.inventory.domain.model.stock.StockRepository;

//@Component
public class StockEventListener {

	
    private static final Logger logger = LoggerFactory
            .getLogger(InventoryApplication.class);
    
    @Autowired
    private ProductApplicationService productApplicationService;
    @Autowired
    private StockRepository stockRepository;


    @JmsListener(destination = "STOCK_CREATED_QUEUE")
    public void handleStockCreatedEvent(Map<String, String> mesage) {


        System.out.println("In StockCreatedListener handleEvent \n\n" + mesage);

        String productId = mesage.get("productId");
        String stockId = mesage.get("stockId");
        String eventVersion = mesage.get("eventVersion");
        String occurredOn = mesage.get("occurredOn");


        System.out.println("\nRecieved message for Stock Created Event");
        System.out.println("productId = " + productId);
        System.out.println("stockId = " + stockId);
        System.out.println("eventVersion = " + eventVersion);
        System.out.println("occurredOn = " + occurredOn);


        this.productApplicationService()
                .assignedStockToProduct(
                        new AssignedStockCommand(
                                productId,
                                stockId
                        )
                );

    }
    
    @JmsListener(destination = "STOCK_THRESHOLD_REACHED_QUEUE")
    public void handleStockThresholdReachedEvent(Map<String, String> mesage) {


    	logger.debug("\n"+mesage.toString());


    }

    
    @JmsListener(destination = "STOCK_EMPTIED_QUEUE")
    public void handleStockEmptiedEvent(Map<String, String> mesage) {

    	logger.debug("\n"+mesage.toString());


    }

    
    
    @JmsListener(destination = "STOCK_QUANTITY_CHANGED_QUEUE")
    public void handleStockQuantityEvent(Map<String, String> mesage) {


    	logger.debug("\n"+mesage.toString());
    	System.out.println(" \n In STOCK_QUANTITY_CHANGED_QUEUE message ===== "+mesage.toString());
    
    	//TO DOOOO


    }



    public ProductApplicationService productApplicationService() {
        return productApplicationService;
    }

    public StockRepository stockRepository() {
        return this.stockRepository;
    }
}

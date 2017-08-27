package com.dddtraining.inventory.port.adapter.messaging.ativemq;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

import com.dddtraining.inventory.InventoryApplication;
import com.dddtraining.inventory.application.stock.StockApplicationService;
import com.dddtraining.inventory.domain.model.stock.StockRepository;

//@Component
public class ProductEventListener {
	
    private static final Logger logger = LoggerFactory
            .getLogger(InventoryApplication.class);
    
    
    @Autowired
    private StockApplicationService stockApplicationService;
    @Autowired
    private StockRepository stockRepository;


    @JmsListener(destination = "PRODUCT_CREATED_QUEUE")
    public void handleProductCreatedEvent(Map<String, String> mesage) {


    	
    	logger.debug("\n"+mesage.toString());

        String productId = mesage.get("productId");
        String name = mesage.get("name");
        String description = mesage.get("description");
        String status = mesage.get("status");
        String enventVersion = mesage.get("enventVersion");
        String occurredOn = mesage.get("occurredOn");


        System.out.println("Recieved message for ProductCreated Event\n");
        System.out.println("productId = " + productId);
        System.out.println("name = " + name);
        System.out.println("description = " + description);
        System.out.println("status = " + status);
        System.out.println("enventVersion = " + enventVersion);
        System.out.println("occurredOn = " + occurredOn);


    }
    
    
    @JmsListener(destination = "PRODUCT_STOCK_ASSIGNED_QUEUE")
    public void handleProductStockAssignedEvent(Map<String, String> mesage) {
    	
    	logger.debug("\n"+mesage.toString());

    	


    }


    public StockApplicationService stockApplicationService() {
        return stockApplicationService;
    }
}

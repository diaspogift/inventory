package com.dddtraining.inventory.port.adapter.messaging.ativemq;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.dddtraining.inventory.InventoryApplication;
import com.dddtraining.inventory.application.arrivage.ArrivageApplicationService;
import com.dddtraining.inventory.application.command.AdjustStockArrivageQuantityCommand;
import com.dddtraining.inventory.application.command.RegisterNewStockProductArrivageCommand;
import com.dddtraining.inventory.application.product.ProductApplicationService;
import com.dddtraining.inventory.application.stock.StockApplicationService;

import javax.persistence.NoResultException;

@Component
public class ArrivageEventListener {
	
	
    private static final Logger logger = LoggerFactory
            .getLogger(InventoryApplication.class);

    @Autowired
    private StockApplicationService stockApplicationService;
    @Autowired
    private ProductApplicationService productApplicationService;
    @Autowired
    private ArrivageApplicationService arrivageApplicationService;

    @JmsListener(destination = "NEW_ARRIVAGE_CREATED_QUEUE")
    public void handleNewArrivageCreatedEvent(Map<String, String> mesage) {


        String stockId = mesage.get("stockId");
        String arrivageId = mesage.get("arrivageId");
        String productId = mesage.get("productId");
        String quantity = mesage.get("quantity");
        String unitPrice = mesage.get("unitPrice");
        String description = mesage.get("description");
        String lifeSpanTimeStartDate = mesage.get("lifeSpanTimeStartDate");
        String lifeSpanTimeEndDate = mesage.get("lifeSpanTimeEndDate");
        String enventVersion = mesage.get("enventVersion");
        String occurredOn = mesage.get("occurredOn");



        this.stockApplicationService()
                .addProductArrivageToStock(
                        new RegisterNewStockProductArrivageCommand(
                                stockId,
                                description,
                                new BigDecimal(unitPrice),
                                new Integer(quantity),
                                arrivageId,
                                productId));

    }

    @JmsListener(destination = "ARRIVAGE_QUANTITY_CHANGED_QUEUE")
    public void handleArrivageQuantityChangedEvent(Map<String, String> message) {

        String arrivageId = message.get("arrivageId");
        String stockId = message.get("stockId");
        int quantity = Integer.parseInt(message.get("quantity"));

   
        
        this.stockApplicationService()
        .adjustStockArrivageQuantity(
        		new AdjustStockArrivageQuantityCommand(
        				arrivageId,
        				stockId,
        				quantity));

     
    }


    public StockApplicationService stockApplicationService() {
        return this.stockApplicationService;
    }

    public ProductApplicationService productApplicationService() {
        return this.productApplicationService;
    }

    public ArrivageApplicationService arrivageApplicationService() {
        return arrivageApplicationService;
    }
}

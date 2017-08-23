package com.dddtraining.inventory.infrastructure.messaging;

import com.dddtraining.inventory.application.ProductApplicationService;
import com.dddtraining.inventory.application.StockApplicationService;
import com.dddtraining.inventory.application.command.RegisterNewStockProductArrivageCommand;
import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.LifeSpanTime;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class NewArrivageCreatedListener {

    @Autowired
    private StockApplicationService stockApplicationService;
    @Autowired
    private ProductApplicationService productApplicationService;

    @JmsListener(destination = "NEW_ARRIVAGE_CREATED_QUEUE")
    public void handleEvent(Map<String, String> mesage){


        Map<String, String> message = new HashMap<String, String>();

        String stockId =  mesage.get("stockId");
        String arrivageId =  mesage.get("arrivageId");
        String productId =  mesage.get("productId");
        String quantity =  mesage.get("quantity");
        String unitPrice =  mesage.get("unitPrice");
        String description =  mesage.get("description");
        String lifeSpanTimeStartDate = mesage.get("lifeSpanTimeStartDate");
        String lifeSpanTimeEndDate = mesage.get("lifeSpanTimeEndDate");
        String enventVersion =  mesage.get("enventVersion");
        String occurredOn = mesage.get("occurredOn");


        System.out.println("\n Recieved message for NewArrivageCreated Event");
        System.out.println("stockId = "+stockId);
        System.out.println("arrivageId = "+arrivageId);
        System.out.println("productId = "+productId);
        System.out.println("quantity = "+quantity);
        System.out.println("unitPrice = "+unitPrice);
        System.out.println("description = "+description);
        System.out.println("lifeSpanTimeStartDate = "+lifeSpanTimeStartDate);
        System.out.println("lifeSpanTimeEndDate = "+lifeSpanTimeEndDate);
        System.out.println("enventVersion = "+enventVersion);
        System.out.println("occurredOn = "+occurredOn);


        this.stockApplicationService()
                .addProductArrivageToStock(
                        new RegisterNewStockProductArrivageCommand(
                        stockId,
                        description,
                        new BigDecimal(unitPrice),
                        new Integer(quantity),
                        arrivageId,
                        productId));

        /*this.productApplicationService()
                .addProductArrivage(
                        new RegisterProductArrivageCommand(
                                productId,
                                arrivageId,
                                new Integer(quantity),
                                new BigDecimal(unitPrice),
                                "ddd"
                        )
                );*/
    }


    public StockApplicationService stockApplicationService() {
        return this.stockApplicationService;
    }

    public ProductApplicationService productApplicationService() {
        return this.productApplicationService;
    }
}

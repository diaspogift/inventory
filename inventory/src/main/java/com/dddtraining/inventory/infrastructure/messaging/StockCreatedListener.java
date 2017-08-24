package com.dddtraining.inventory.infrastructure.messaging;

import com.dddtraining.inventory.application.ProductApplicationService;
import com.dddtraining.inventory.application.StockApplicationService;
import com.dddtraining.inventory.application.command.AssignedStockCommand;
import com.dddtraining.inventory.application.command.CreateStockCommand;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class StockCreatedListener {

    @Autowired
    private ProductApplicationService productApplicationService;
    @Autowired
    private StockRepository stockRepository;


    @JmsListener(destination = "STOCK_CREATED_QUEUE")
    public void handleEvent(Map<String, String> mesage){



        System.out.println("In StockCreatedListener handleEvent \n\n"+ mesage);

        String productId =  mesage.get("productId");
        String stockId =  mesage.get("stockId");
        String eventVersion =  mesage.get("eventVersion");
        String occurredOn = mesage.get("occurredOn");


        System.out.println("\nRecieved message for Stock Created Event");
        System.out.println("productId = "+productId);
        System.out.println("stockId = "+stockId);
        System.out.println("eventVersion = "+eventVersion);
        System.out.println("occurredOn = "+occurredOn);




        this.productApplicationService()
                .assignedStockToProduct(
                        new AssignedStockCommand(
                                productId,
                                stockId
                        )
                );

    }


    public ProductApplicationService productApplicationService() {
        return productApplicationService;
    }

    public StockRepository stockRepository() {
        return this.stockRepository;
    }
}

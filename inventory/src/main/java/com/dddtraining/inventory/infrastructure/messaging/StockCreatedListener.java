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


        Set<Stock> allStocks = this.stockRepository.allStocks();


        System.out.println(allStocks.size() +"  I am the ProductCreatedListener2 \n\n");


        for(Stock nextStock : allStocks){
            System.out.println("\n\n"+ nextStock.toString());
        }



        String productId =  mesage.get("productId");
        String stockId =  mesage.get("stockId");
        String enventVersion =  mesage.get("enventVersion");
        String occurredOn = mesage.get("occurredOn");


        System.out.println("Recieved message for Stock Created Event \n\n");
        System.out.println("productId = "+productId);
        System.out.println("stockId = "+stockId);
        System.out.println("enventVersion = "+enventVersion);
        System.out.println("occurredOn = "+occurredOn);
        System.out.println("\n\n");




        this.productApplicationService()
                .assignedStockToProduct(
                        new AssignedStockCommand(
                                productId,
                                stockId
                        )
                );


        allStocks = this.stockRepository.allStocks();


        System.out.println(allStocks.size() +" Stock Repo after \n\n");

        for(Stock nextStock : allStocks){
            System.out.println("\n\n"+ nextStock.toString());
        }

    }


    public ProductApplicationService productApplicationService() {
        return productApplicationService;
    }

    public StockRepository stockRepository() {
        return this.stockRepository;
    }
}

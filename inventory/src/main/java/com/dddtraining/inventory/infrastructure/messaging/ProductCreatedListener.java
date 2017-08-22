package com.dddtraining.inventory.infrastructure.messaging;

import com.dddtraining.inventory.application.StockApplicationService;
import com.dddtraining.inventory.application.command.CreateStockCommand;
import com.dddtraining.inventory.domain.model.product.ProductCreated;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ProductCreatedListener {

    @Autowired
    private StockApplicationService stockApplicationService;
    @Autowired
    private StockRepository stockRepository;


    @JmsListener(destination = "PRODUCT_CREATED_QUEUE")
    public void handleEvent(Map<String, String> mesage){

        StockId stockId = stockRepository.nextIdentity();

        Set<Stock> allStocks = this.stockRepository.allStocks();


        System.out.println(allStocks.size() +"  Stock Repo before \n\n");

        for(Stock nextStock : allStocks){
            System.out.println("\n\n"+ nextStock.toString());
        }



        String productId =  mesage.get("productId");
        String name =  mesage.get("name");
        String description =  mesage.get("description");
        String status =  mesage.get("status");
        String enventVersion =  mesage.get("enventVersion");
        String occurredOn = mesage.get("occurredOn");


        System.out.println("Recieved message for ProductCreated Event \n\n");
        System.out.println("productId = "+productId);
        System.out.println("name = "+name);
        System.out.println("description = "+description);
        System.out.println("status = "+status);
        System.out.println("enventVersion = "+enventVersion);
        System.out.println("occurredOn = "+occurredOn);
        System.out.println("\n\n");




        this.stockApplicationService()
                .createStock(
                        new CreateStockCommand(
                        stockId.id(),
                        productId,
                                0
                ));

      allStocks = this.stockRepository.allStocks();


        System.out.println(allStocks.size() +" Stock Repo after \n\n");

        for(Stock nextStock : allStocks){
            System.out.println("\n\n"+ nextStock.toString());
        }

    }


    public StockApplicationService stockApplicationService() {
        return stockApplicationService;
    }
}

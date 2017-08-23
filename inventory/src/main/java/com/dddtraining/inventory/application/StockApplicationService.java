package com.dddtraining.inventory.application;

import com.dddtraining.inventory.application.command.CreateStockCommand;
import com.dddtraining.inventory.application.command.RegisterNewStockProductArrivageCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.common.DomainEventSubscriber;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Service
public class StockApplicationService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JmsTemplate jmsTemplate;


    @Transactional(readOnly = true)
    public Stock stock(String aStockId){

        Stock stock =
                this.stockRepository()
                .stockOfId(new StockId(aStockId));

        return stock;
    }


    @Transactional
    public void createStock(CreateStockCommand aCommand){


        DomainEventSubscriber<StockCreated> subscriber =
                new DomainEventSubscriber<StockCreated>() {
                    @Override
                    public void handleEvent(StockCreated aDomainEvent) {

                        Map<String, String> message = new HashMap<String, String>();

                        message.put("productId", aDomainEvent.productId().id());
                        message.put("stockId", aDomainEvent.stockId().id());
                        message.put("eventVersion", String.valueOf(aDomainEvent.eventVersion()));
                        message.put("occurredOn", String.valueOf(aDomainEvent.occurredOn()));

                        jmsTemplate.convertAndSend("STOCK_CREATED_QUEUE",message);
                    }

                    @Override
                    public Class<StockCreated> subscribedToEventType() {
                        return StockCreated.class;
                    }
                };

        DomainEventPublisher.instance().subscribe(subscriber);

        StockId stockId = new StockId(aCommand.stockId());
        ProductId productId = new ProductId(aCommand.productId());
        Quantity quantity = new Quantity(aCommand.quantity());



        this.stockRepository()
                .add(new Stock(
                        stockId,
                        productId,
                        quantity
                ));

    }


     public void addProductArrivageToStock(RegisterNewStockProductArrivageCommand aCommand){

         Product product =
                 this.productRepository()
                 .productOfId(
                         new ProductId(
                                 aCommand.productId()));


         System.out.println("\n\n HERE IS MY aCommand \n\n"+aCommand.toString());
         System.out.println("\n\n HERE IS MY PRODUCT \n\n"+product.toString());


        Stock stock =
                this.stockRepository()
                .stockOfId(product.stockId());



        if(stock != null){

            stock.addNewStockProductArrivage(
                    new Arrivage(
                            new ProductId(aCommand.productId()),
                            product.stockId(),
                            new ArrivageId(aCommand.arrivageId()),
                            new Quantity(aCommand.quantity()),
                            aCommand.unitPrice(),
                            aCommand.description()
                    )
            );
        }

         Stock stocks  =
                 this.stockRepository()
                         .stockForProductOfId(
                                 new ProductId("PROD_3333"));

         Set<StockProductArrivage> all =
                 stocks.stockProductArrivages();

         for(StockProductArrivage next : all){

             System.out.println("\n\n StockProductArrivage "+next);
         }


         System.out.println("HERE IS MY PRODUCT in addProductArrivageToStock \n\n"+product.toString());



     }



    private StockRepository stockRepository() {
        return this.stockRepository;
    }

    public ProductRepository productRepository() {
        return this.productRepository;
    }
}

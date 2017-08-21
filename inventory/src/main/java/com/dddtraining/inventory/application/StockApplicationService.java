package com.dddtraining.inventory.application;

import com.dddtraining.inventory.application.command.CreateStockCommand;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.common.DomainEventSubscriber;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StockApplicationService {

    @Autowired
    StockRepository stockRepository;
    @Autowired
    ProductRepository productRepository;


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

                        //TODO the event will be handled here!
                        System.out.println("\n\n\n Event "+aDomainEvent.toString()+"\n\n");
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



    private StockRepository stockRepository() {
        return this.stockRepository;
    }

}

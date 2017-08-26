package com.dddtraining.inventory.application.stock;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dddtraining.inventory.InventoryApplication;
import com.dddtraining.inventory.application.command.CreateStockCommand;
import com.dddtraining.inventory.application.command.DecrementProductStockCommand;
import com.dddtraining.inventory.application.command.RegisterNewStockProductArrivageCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.common.DomainEventSubscriber;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockCreated;
import com.dddtraining.inventory.domain.model.stock.StockEmptied;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockProductArrivage;
import com.dddtraining.inventory.domain.model.stock.StockQuantityChanged;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import com.dddtraining.inventory.domain.model.stock.StockThresholdReached;


@Service
public class StockApplicationService {


    private static final Logger logger = LoggerFactory
            .getLogger(InventoryApplication.class);

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JmsTemplate jmsTemplate;


    @Transactional(readOnly = true)
    public Stock stock(String aStockId) {

        Stock stock =
                this.stockRepository()
                        .stockOfId(new StockId(aStockId));

        return stock;
    }


    @Transactional
    public void createStock(CreateStockCommand aCommand) {


        DomainEventSubscriber<StockCreated> subscriber =
                new DomainEventSubscriber<StockCreated>() {
                    @Override
                    public void handleEvent(StockCreated aDomainEvent) {

                        Map<String, String> message = new HashMap<String, String>();

                        message.put("productId", aDomainEvent.productId().id());
                        message.put("stockId", aDomainEvent.stockId().id());
                        message.put("eventVersion", String.valueOf(aDomainEvent.eventVersion()));
                        message.put("occurredOn", String.valueOf(aDomainEvent.occurredOn()));

                        jmsTemplate.convertAndSend("STOCK_CREATED_QUEUE", message);
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


    public void addProductArrivageToStock(RegisterNewStockProductArrivageCommand aCommand) {


        Product product =
                this.productRepository()
                        .productOfId(
                                new ProductId(
                                        aCommand.productId()));


        Stock stock =
                this.stockRepository()
                        .stockOfId(product.stockId());


        if (stock != null) {

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


    }

    @Transactional
    public  void decrementStockOf(DecrementProductStockCommand aCommand){


        DomainEventSubscriber<StockEmptied> subscriber1 =
                new DomainEventSubscriber<StockEmptied>() {
                    @Override
                    public void handleEvent(StockEmptied aDomainEvent) {

                        Map<String, String> message = new HashMap<String, String>();

                        message.put("stockId", aDomainEvent.stockId().id());
                        message.put("productId", aDomainEvent.productId().id());
                        message.put("quantity", String.valueOf(aDomainEvent.quantity().value()));
                        message.put("eventVersion", String.valueOf(aDomainEvent.eventVersion()));
                        message.put("occurredOn", String.valueOf(aDomainEvent.occurredOn()));



                        jmsTemplate.convertAndSend("STOCK_EMPTIED_QUEUE", message);
                    }

                    @Override
                    public Class<StockEmptied> subscribedToEventType() {
                        return StockEmptied.class;
                    }
                };

        DomainEventSubscriber<StockThresholdReached> subscriber2 =
                new DomainEventSubscriber<StockThresholdReached>() {
                    @Override
                    public void handleEvent(StockThresholdReached aDomainEvent) {

                        Map<String, String> message = new HashMap<String, String>();

                        message.put("productId", aDomainEvent.productId().id());
                        message.put("stockId", aDomainEvent.stockId().id());
                        message.put("quantity", String.valueOf(aDomainEvent.quantity().value()));
                        message.put("eventVersion", String.valueOf(aDomainEvent.eventVersion()));
                        message.put("occurredOn", String.valueOf(aDomainEvent.occurredOn()));

                        jmsTemplate.convertAndSend("STOCK_THRESHOLD_REACHED_QUEUE", message);
                    }

                    @Override
                    public Class<StockThresholdReached> subscribedToEventType() {
                        return StockThresholdReached.class;
                    }
                };


        DomainEventSubscriber<StockQuantityChanged> subscriber3 =
                new DomainEventSubscriber<StockQuantityChanged>() {
                    @Override
                    public void handleEvent(StockQuantityChanged aDomainEvent) {

                        Map<String, String> message = new HashMap<String, String>();

                        message.put("productId", aDomainEvent.productId().id());
                        message.put("stockId", aDomainEvent.stockId().id());
                        message.put("quantity", String.valueOf(aDomainEvent.quantity().value()));
                        message.put("eventVersion", String.valueOf(aDomainEvent.eventVersion()));
                        message.put("occurredOn", String.valueOf(aDomainEvent.occurredOn()));

                        for(StockProductArrivage next : aDomainEvent.allModifiedArrivages()){
                            message.put(next.arrivageId().id(), String.valueOf(next.quantity().value()));
                        }


                        logger.debug(aDomainEvent.toString());

                        jmsTemplate.convertAndSend("STOCK_STOCK_QUANTY_CHANGED_QUEUE", message);
                    }

                    @Override
                    public Class<StockQuantityChanged> subscribedToEventType() {
                        return StockQuantityChanged.class;
                    }
                };

        Stock stock =
                this.stockRepository()
                        .stockOfId(new StockId(aCommand.stockId()));

        DomainEventPublisher.instance().subscribe(subscriber1);
        DomainEventPublisher.instance().subscribe(subscriber2);
        DomainEventPublisher.instance().subscribe(subscriber3);



        if(stock != null)
            stock.clearStockOf(aCommand.quantity());


    }




    private StockRepository stockRepository() {
        return this.stockRepository;
    }

    public ProductRepository productRepository() {
        return this.productRepository;
    }
}

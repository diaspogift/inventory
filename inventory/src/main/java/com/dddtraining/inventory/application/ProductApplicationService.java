package com.dddtraining.inventory.application;

import com.dddtraining.inventory.application.command.*;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.arrivage.NewArrivageCreated;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.common.DomainEventSubscriber;
import com.dddtraining.inventory.domain.model.product.*;
import com.dddtraining.inventory.domain.model.stock.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ProductApplicationService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArrivageRepository arrivageRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private JmsTemplate jmsTemplate;


    @Transactional(readOnly = true)
    public Product product(String aProductId){

        Product product =
                this.productRepository()
                .productOfId(new ProductId(aProductId));

        return  product;
    }

    @Transactional(readOnly = true)
    public Set<Product> products(){

        Set<Product> products  =
                this.productRepository().allProducts();

        return  products;
    }


    @Transactional
    public void addProduct(RegisterProductCommand aCommand){

        DomainEventSubscriber<ProductCreated> subscriber =
                new DomainEventSubscriber<ProductCreated>() {
                    @Override
                    public void handleEvent(ProductCreated aDomainEvent) {

                        System.out.println("\n\n\n HERE MY EVENT"+ aDomainEvent);


                        Map<String, String> message = new HashMap<String, String>();

                        message.put("productId", aDomainEvent.productId().id());
                        message.put("name", aDomainEvent.name());
                        message.put("description", aDomainEvent.description());
                        message.put("status", aDomainEvent.status().name());
                        message.put("enventVersion", String.valueOf(aDomainEvent.eventVersion()));
                        message.put("occurredOn", aDomainEvent.occurredOn().toString());


                        jmsTemplate.convertAndSend("PRODUCT_CREATED_QUEUE", message);
                        jmsTemplate.convertAndSend("CATALOG_PRODUCT_CREATED_QUEUE", message);

                    }

                    @Override
                    public Class<ProductCreated> subscribedToEventType() {
                        return ProductCreated.class;
                    }
                };

        DomainEventPublisher.instance().subscribe(subscriber);



        ProductId productId = new ProductId(aCommand.productId());

        System.out.println(" \n\n HERE IS MY PRODUCT ID TO ADD "+aCommand.productId());

        this.productRepository()
                .add(new Product(
                        productId,
                        aCommand.name(),
                        aCommand.description()));


    }

    @Transactional
    public Arrivage addProductArrivage(RegisterProductArrivageCommand aCommand){

        System.out.println("HERE in addProductArrivage ProdApplicationService aCommand\n\n\n "+aCommand.toString());


        DomainEventSubscriber<NewArrivageCreated> subscriber =
                new DomainEventSubscriber<NewArrivageCreated>() {
                    @Override
                    public void handleEvent(NewArrivageCreated aDomainEvent) {


                        System.out.println("\n\n HERE MY EVENT"+ aDomainEvent);

                        Map<String, String> message = new HashMap<String, String>();

                        message.put("arrivageId", aDomainEvent.arrivageId().id());
                        message.put("productId", aDomainEvent.productId().id());
                        message.put("stockId", aDomainEvent.stockId().id());
                        message.put("quantity", String.valueOf(aDomainEvent.quantity().value()));
                        message.put("unitPrice", String.valueOf(aDomainEvent.unitPrice()));
                        message.put("description", aDomainEvent.description());
                        message.put("lifeSpanTimeStartDate", String.valueOf(aDomainEvent.lifeSpanTime().startDate()));
                        message.put("lifeSpanTimeEndDate", String.valueOf(aDomainEvent.lifeSpanTime().endDate()));
                        message.put("enventVersion", String.valueOf(aDomainEvent.eventVersion()));
                        message.put("occurredOn", aDomainEvent.occurredOn().toString());

                        jmsTemplate.convertAndSend("NEW_ARRIVAGE_CREATED_QUEUE", message);



                    }

                    @Override
                    public Class<NewArrivageCreated> subscribedToEventType() {
                        return NewArrivageCreated.class;
                    }
                };

        DomainEventPublisher.instance().subscribe(subscriber);


        Product product =
                this.productRepository()
                    .productOfId(new ProductId(
                            aCommand.productId()
                    ));


        Arrivage productArrivage =
                product.createNewArrivage(
                        new ArrivageId(aCommand.arrivageId()),
                        new Quantity(aCommand.quantity()),
                        aCommand.unitPrice(),
                        aCommand.description());


        this.arrivageRepository().add(productArrivage);


        DomainEventPublisher.instance().unSubscribe(subscriber);

        return  productArrivage;
    }


    @Transactional
    public void creatProductStock(CreateProductStockCommand aCommand){

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


        ProductId productId = new ProductId(aCommand.productId());

        Product product =
                this.productRepository()
                .productOfId(productId);

        if(product != null)
        {
            Stock stock =
                    product.createStock(
                            new StockId(aCommand.stockId()),
                            aCommand.theshold());


            this.stockRepository().add(stock);
        }



    }

    @Transactional
    public void decrementProductStock(DecrementProductStockCommand aCommand){


        DomainEventSubscriber<StockThresholdReached> subscriber1 =
                new DomainEventSubscriber<StockThresholdReached>() {
                    @Override
                    public void handleEvent(StockThresholdReached aDomainEvent) {

                        System.out.println("\n\n\n HERE MY EVENT "+aDomainEvent.toString()+"\n\n");
                    }

                    @Override
                    public Class<StockThresholdReached> subscribedToEventType() {
                        return StockThresholdReached.class;
                    }
                };


        DomainEventSubscriber<StockEmptied> subscriber2 =
                new DomainEventSubscriber<StockEmptied>() {
                    @Override
                    public void handleEvent(StockEmptied aDomainEvent) {

                        //TODO the event will be handled here!
                        System.out.println("\n\n\n Event "+aDomainEvent.toString()+"\n\n");
                    }

                    @Override
                    public Class<StockEmptied> subscribedToEventType() {
                        return StockEmptied.class;
                    }
                };
        DomainEventSubscriber<ArrivageQuantityDecremented> subscriber3 =
                new DomainEventSubscriber<ArrivageQuantityDecremented>() {
                    @Override
                    public void handleEvent(ArrivageQuantityDecremented aDomainEvent) {

                        System.out.println("\n\n\n HERE MY EVENT "+aDomainEvent.toString()+"\n\n");
                    }

                    @Override
                    public Class<ArrivageQuantityDecremented> subscribedToEventType() {
                        return ArrivageQuantityDecremented.class;
                    }
                };


        DomainEventPublisher.instance().subscribe(subscriber1);
        DomainEventPublisher.instance().subscribe(subscriber2);
        DomainEventPublisher.instance().subscribe(subscriber3);




        Stock stock =
                this.stockRepository()
                        .stockForProductOfId((
                                new ProductId(aCommand.productId())));

        if(stock != null )

            stock.clearStockOf(aCommand.quantity());

    }

    @Transactional
    public void augmentProductStock(AugmentProductStockCommand aCommand){

        Stock stock =
                this.stockRepository()
                        .stockForProductOfId((
                                new ProductId(aCommand.productId())));

        if(stock != null )

            stock.augmentStockOf(aCommand.quantity());

    }


    @Transactional
    public void assignedStockToProduct(AssignedStockCommand aCommand){


        System.out.println("\n\n\n AssignedStockCommand  = "+ aCommand.toString());


        DomainEventSubscriber<ProductStockAssigned> subscriber =
                new DomainEventSubscriber<ProductStockAssigned>() {
                    @Override
                    public void handleEvent(ProductStockAssigned aDomainEvent) {


                        System.out.println("\n\n\n HERE MY EVENT  "+ aDomainEvent.toString());



                        Map<String, String> message = new HashMap<String, String>();

                        message.put("stockId", aDomainEvent.stockId().id());
                        message.put("productId", aDomainEvent.productId().id());


                        //jmsTemplate.convertAndSend("NEW_ARRIVAGE_CREATED_QUEUE", message);
                    }

                    @Override
                    public Class<ProductStockAssigned> subscribedToEventType() {
                        return ProductStockAssigned.class;
                    }
                };

        DomainEventPublisher.instance().subscribe(subscriber);


        Set<Product>  products =
                this.productRepository()
                .allProducts();


        Product product =
                this.productRepository()
                .productOfId(
                        new ProductId(aCommand.productId()));

        Stock stock = new Stock(
                new StockId(aCommand.stockId()),
                new ProductId(aCommand.productId()));


        System.out.println("\n\n\n HERE MY STOCK   "+ stock);


        if(stock != null){
            product.assignStock(stock);
        }



    }


    //TODO not tested
    @Transactional(readOnly = true)
    public Set<Arrivage> productArrivages(String productId){

        Set<Arrivage> arrivages =
                this.arrivageRepository()
                .allArrivagesOfProductId(new ProductId(productId));

        return arrivages;
    }




    private StockRepository stockRepository() {
        return this.stockRepository;
    }
    private ArrivageRepository arrivageRepository() {
        return  this.arrivageRepository;
    }
    private ProductRepository productRepository() {
        return this.productRepository;
    }
    public JmsTemplate jmsTemplate() {
        return this.jmsTemplate;
    }
}

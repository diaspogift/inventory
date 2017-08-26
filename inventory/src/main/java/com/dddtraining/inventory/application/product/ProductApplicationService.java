package com.dddtraining.inventory.application.product;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dddtraining.inventory.application.command.AssignedStockCommand;
import com.dddtraining.inventory.application.command.AugmentProductStockCommand;
import com.dddtraining.inventory.application.command.CreateProductStockCommand;
import com.dddtraining.inventory.application.command.DecrementProductStockCommand;
import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.application.command.RegisterProductCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.arrivage.NewArrivageCreated;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.common.DomainEventSubscriber;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductCreated;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.product.ProductStockAssigned;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockCreated;
import com.dddtraining.inventory.domain.model.stock.StockEmptied;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockQuantityChanged;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import com.dddtraining.inventory.domain.model.stock.StockThresholdReached;

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
    public Product product(String aProductId) {

        Product product =
                this.productRepository()
                        .productOfId(new ProductId(aProductId));

        return product;
    }

    @Transactional(readOnly = true)
    public Set<Product> products() {

        Set<Product> products =
                this.productRepository().allProducts();

        return products;
    }


    @Transactional
    public void addProduct(RegisterProductCommand aCommand) {

        DomainEventSubscriber<ProductCreated> subscriber =
                new DomainEventSubscriber<ProductCreated>() {
                    @Override
                    public void handleEvent(ProductCreated aDomainEvent) {



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


        this.productRepository()
                .add(new Product(
                        productId,
                        aCommand.name(),
                        aCommand.description()));


    }

    @Transactional
    public Arrivage addProductArrivage(RegisterProductArrivageCommand aCommand) {


        DomainEventSubscriber<NewArrivageCreated> subscriber =
                new DomainEventSubscriber<NewArrivageCreated>() {
                    @Override
                    public void handleEvent(NewArrivageCreated aDomainEvent) {



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

        return productArrivage;
    }


    @Transactional
    public void creatProductStock(CreateProductStockCommand aCommand) {

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


        ProductId productId = new ProductId(aCommand.productId());

        Product product =
                this.productRepository()
                        .productOfId(productId);

        if (product != null) {
            Stock stock =
                    product.createStock(
                            new StockId(aCommand.stockId()),
                            aCommand.theshold());


            this.stockRepository().add(stock);
        }


    }

    @Transactional
    public void decrementProductStock(DecrementProductStockCommand aCommand) {


        DomainEventSubscriber<StockThresholdReached> subscriber1 =
                new DomainEventSubscriber<StockThresholdReached>() {
                    @Override
                    public void handleEvent(StockThresholdReached aDomainEvent) {
                    	
                	Map<String, String> message = new HashMap<String, String>();

                    	
                    	message.put("stockId", aDomainEvent.stockId().id());
                    	message.put("productId", aDomainEvent.productId().id());

                    	jmsTemplate.convertAndSend("STOCK_THRESHOLD_REACHED_QUEUE", message);
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
                    	
                    	Map<String, String> message = new HashMap<String, String>();

                    	
                    	message.put("stockId", aDomainEvent.stockId().id());
                    	message.put("productId", aDomainEvent.productId().id());

                    	jmsTemplate.convertAndSend("STOCK_EMPTIED_QUEUE", message);
                    }

                    @Override
                    public Class<StockEmptied> subscribedToEventType() {
                        return StockEmptied.class;
                    }
                };
        DomainEventSubscriber<StockQuantityChanged> subscriber3 =
                new DomainEventSubscriber<StockQuantityChanged>() {
                    @Override
                    public void handleEvent(StockQuantityChanged aDomainEvent) {
                    	
                    	Map<String, String> message = new HashMap<String, String>();
                    	
                    	message.put("stockId", aDomainEvent.stockId().id());
                    	message.put("productId", aDomainEvent.productId().id());

                    	jmsTemplate.convertAndSend("STOCK_QUANTITY_CHANGED_QUEUE", message);
                    }

                    @Override
                    public Class<StockQuantityChanged> subscribedToEventType() {
                        return StockQuantityChanged.class;
                    }
                };


        DomainEventPublisher.instance().subscribe(subscriber1);
        DomainEventPublisher.instance().subscribe(subscriber2);
        DomainEventPublisher.instance().subscribe(subscriber3);


        Stock stock =
                this.stockRepository()
                        .stockForProductOfId((
                                new ProductId(aCommand.productId())));

        if (stock != null)

            stock.clearStockOf(aCommand.quantity());

    }

    @Transactional
    public void augmentProductStock(AugmentProductStockCommand aCommand) {

        Stock stock =
                this.stockRepository()
                        .stockForProductOfId((
                                new ProductId(aCommand.productId())));

        if (stock != null)

            stock.augmentStockOf(aCommand.quantity());

    }


    @Transactional
    public void assignedStockToProduct(AssignedStockCommand aCommand) {



        DomainEventSubscriber<ProductStockAssigned> subscriber =
                new DomainEventSubscriber<ProductStockAssigned>() {
                    @Override
                    public void handleEvent(ProductStockAssigned aDomainEvent) {




                        Map<String, String> message = new HashMap<String, String>();

                        message.put("stockId", aDomainEvent.stockId().id());
                        message.put("productId", aDomainEvent.productId().id());


                        jmsTemplate.convertAndSend("PRODUCT_STOCK_ASSIGNED_QUEUE", message);
                    }

                    @Override
                    public Class<ProductStockAssigned> subscribedToEventType() {
                        return ProductStockAssigned.class;
                    }
                };

        DomainEventPublisher.instance().subscribe(subscriber);


        Set<Product> products =
                this.productRepository()
                        .allProducts();


        Product product =
                this.productRepository()
                        .productOfId(
                                new ProductId(aCommand.productId()));

        Stock stock = new Stock(
                new StockId(aCommand.stockId()),
                new ProductId(aCommand.productId()));




        if (stock != null && product != null) {
            product.assignStock(stock);
        }


    }


    //TODO not tested
    @Transactional(readOnly = true)
    public Set<Arrivage> productArrivages(String productId) {

        Set<Arrivage> arrivages =
                this.arrivageRepository()
                        .allArrivagesOfProductId(new ProductId(productId));

        return arrivages;
    }


    private StockRepository stockRepository() {
        return this.stockRepository;
    }

    private ArrivageRepository arrivageRepository() {
        return this.arrivageRepository;
    }

    private ProductRepository productRepository() {
        return this.productRepository;
    }

    public JmsTemplate jmsTemplate() {
        return this.jmsTemplate;
    }
}
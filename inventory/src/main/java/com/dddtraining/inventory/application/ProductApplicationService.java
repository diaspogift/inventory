package com.dddtraining.inventory.application;

import com.dddtraining.inventory.application.command.AugmentProductStockCommand;
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
import com.dddtraining.inventory.domain.model.stock.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ProductApplicationService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArrivageRepository arrivageRepository;
    @Autowired
    private StockRepository stockRepository;


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

                        //TODO the event will be handled here!
                        System.out.println("\n\n\n Event "+aDomainEvent.toString());
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
    public Arrivage addProductArrivage(RegisterProductArrivageCommand aCommand){


        DomainEventSubscriber<NewArrivageCreated> subscriber =
                new DomainEventSubscriber<NewArrivageCreated>() {
                    @Override
                    public void handleEvent(NewArrivageCreated aDomainEvent) {

                        //TODO the event will be handled here!
                        System.out.println("\n\n\n Event "+aDomainEvent.toString());
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

        return  productArrivage;
    }


    @Transactional
    public void decrementProductStock(DecrementProductStockCommand aCommand){


        DomainEventSubscriber<StockThresholdReached> subscriber1 =
                new DomainEventSubscriber<StockThresholdReached>() {
                    @Override
                    public void handleEvent(StockThresholdReached aDomainEvent) {

                        //TODO the event will be handled here!
                        System.out.println("\n\n\n Event "+aDomainEvent.toString()+"\n\n");
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



        DomainEventPublisher.instance().subscribe(subscriber1);
        DomainEventPublisher.instance().subscribe(subscriber2);




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





    private StockRepository stockRepository() {
        return this.stockRepository;
    }
    private ArrivageRepository arrivageRepository() {
        return  this.arrivageRepository;
    }
    private ProductRepository productRepository() {
        return this.productRepository;
    }




}

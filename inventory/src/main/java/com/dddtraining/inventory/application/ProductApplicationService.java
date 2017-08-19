package com.dddtraining.inventory.application;

import com.dddtraining.inventory.application.command.RegisterProductArrivageCommand;
import com.dddtraining.inventory.application.command.RegisterProductCommand;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.arrivage.NewArrivageCreated;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.common.DomainEventSubscriber;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ProductApplicationService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ArrivageRepository arrivageRepository;



    public Product product(String aProductId){

        Product product =
                this.productRepository()
                .productOfId(new ProductId(aProductId));

        return  product;
    }


    public void addProduct(RegisterProductCommand aCommand){

        ProductId productId = new ProductId(aCommand.productId());

        this.productRepository()
                .add(new Product(
                        productId,
                        aCommand.name(),
                        aCommand.description()));
    }

    public Arrivage addProductArrivage(RegisterProductArrivageCommand aCommand){


        DomainEventSubscriber<NewArrivageCreated> subscriber =
                new DomainEventSubscriber<NewArrivageCreated>() {
                    @Override
                    public void handleEvent(NewArrivageCreated aDomainEvent) {
                        System.out.println("I was called");
                    }

                    @Override
                    public Class<NewArrivageCreated> subscribedToEventType() {
                        return NewArrivageCreated.class;
                    }
                };




        ProductId productId = new ProductId(aCommand.productId());
        ArrivageId arrivageId = new ArrivageId(aCommand.arrivageId());
        Quantity quantity = new Quantity(aCommand.quantity());


        Product product =
                this.productRepository()
                    .productOfId(productId);


        Arrivage productArrivage =
                product.createNewArrivage(
                        arrivageId,
                        quantity,
                        aCommand.unitPrice(),
                        aCommand.description());

        this.arrivageRepository().add(productArrivage);

        return  productArrivage;
    }



    private ArrivageRepository arrivageRepository() {
        return  this.arrivageRepository;
    }
    private ProductRepository productRepository() {
        return this.productRepository;
    }




}

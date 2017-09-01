package com.dddtraining.inventory.domain.model.product;

import java.math.BigDecimal;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.NewArrivageCreated;
import com.dddtraining.inventory.domain.model.common.ConcurrencySafeEntity;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;

@Entity
public class Product extends ConcurrencySafeEntity {

	@Embedded
    private ProductId productId;
	@Embedded
    private StockId stockId;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status;


    public Product(ProductId aProductId, String aName, String aDescription) {

        this();
        this.setProductId(aProductId);
        this.setName(aName);
        this.setDescription(aDescription);
        this.setStatus(AvailabilityStatus.CREATED);

        //ProductCreated domain event

        DomainEventPublisher
                .instance()
                .publish(new ProductCreated(
                        this.productId(),
                        this.name(),
                        this.description(),
                        AvailabilityStatus.CREATED
                ));


    }

    public Product(ProductId aProductId, StockId aStockId, String aName, String aDescription) {

        this();
        this.setProductId(aProductId);
        this.setStockId(aStockId);
        this.setName(aName);
        this.setDescription(aDescription);
        this.setStatus(AvailabilityStatus.CREATED);

        //ProductCreated domain event

        DomainEventPublisher
                .instance()
                .publish(new ProductCreated(
                        this.productId(),
                        this.name(),
                        this.description(),
                        this.status()
                ));
    }


    //*** Business logic ***//

    public Product() {
        super();


    }

    public Product(ProductId productId2, String string, String string2, AvailabilityStatus availabilityStatus) {
        this();
        this.setProductId(productId2);
        this.setName(string);
        this.setDescription(string2);
        this.setStatus(availabilityStatus);	
        
        DomainEventPublisher
        .instance()
        .publish(new ProductCreated(
                this.productId(),
                this.name(),
                this.description(),
                this.status()
        ));
     }

	public void changeAvailabilityStatus(AvailabilityStatus aStatus) {
        if (this.status() != aStatus) {
            this.setStatus(aStatus);
        }
    }

    public Arrivage createNewArrivage(ArrivageId anArrivageId, Quantity aQuantity, BigDecimal aUnitPrice, String aDescription) {

        this.assertStateFalse(this.hasNoStock(), "Product has no stock associated.");

        Arrivage arrivage =
                new Arrivage(
                        this.productId(),
                        this.stockId(),
                        anArrivageId,
                        aQuantity,
                        aUnitPrice,
                        aDescription);

        System.out.println("\n In createNewArrivage = ");
        System.out.println("\n In createNewArrivage = ");
        System.out.println("\n In createNewArrivage = ");

        DomainEventPublisher
                .instance()
                .publish(
                        new NewArrivageCreated(
                                this.productId(),
                                this.stockId(),
                                anArrivageId,
                                aQuantity,
                                aUnitPrice,
                                aDescription,
                                arrivage.lifeSpanTime()
                        ));
        return arrivage;
    }

    public Stock createStock(StockId aStockId, int aThreshold) {

        Stock stock =
                new Stock(
                        aStockId,
                        this.productId(),
                        new Quantity(0),
                        aThreshold
                );
        return stock;
    }

    public void assignStock(Stock aStock) {


        System.out.println(" \n in assignStock aStock ="+aStock);
        System.out.println(" \n in assignStock aStock ="+aStock);
        System.out.println(" \n in assignStock aStock ="+aStock);

        if (this.hasNoStock()) {
            this.setStockId(aStock.stockId());
            this.elevateAvailabilityStatus(AvailabilityStatus.STOCK_PROVIDED);


            DomainEventPublisher.instance()
                    .publish(
                            new ProductStockAssigned(
                                    aStock.stockId(),
                                    this.productId()
                            )
                    );
        }
    }

    private void elevateAvailabilityStatus(AvailabilityStatus anAvailabilityStatus) {
        this.setStatus(anAvailabilityStatus);
    }

    private boolean hasNoStock() {

        if (this.stockId() == null)
            return true;
        else
            return false;

    }

    //*** Getters and Setters ***//
    private void setProductId(ProductId aProductId) {
        this.assertArgumentNotNull(aProductId, "ProductId is required.");
        this.productId = aProductId;
    }

    private void setStockId(StockId aStockId) {
        this.assertArgumentNotNull(aStockId, "StockId is required.");
        this.stockId = aStockId;
    }

    private void setName(String aName) {
        this.name = aName;
    }

    private void setStatus(AvailabilityStatus aStatus) {
        this.status = aStatus;
    }

    private void setDescription(String aDescription) {
        this.description = aDescription;
    }

    public ProductId productId() {
        return productId;
    }

    public StockId stockId() {
        return this.stockId;
    }

    public String name() {
        return name;
    }

    public AvailabilityStatus status() {
        return this.status;
    }

    public String description() {
        return description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (productId == null) {
            if (other.productId != null)
                return false;
        } else if (!productId.equals(other.productId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", stockId=" + stockId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}

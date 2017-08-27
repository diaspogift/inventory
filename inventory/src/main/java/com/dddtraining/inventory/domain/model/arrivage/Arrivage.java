package com.dddtraining.inventory.domain.model.arrivage;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.StockId;

@Entity
public class Arrivage {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Embedded
    private ArrivageId arrivageId;
	@Embedded
    private StockId stockId;
	@Embedded
    private ProductId productId;
	@Embedded
    private Quantity quantity;
    private BigDecimal unitPrice;
    private String description;
    private boolean isRunOut;
	@Embedded
    private LifeSpanTime lifeSpanTime;


    public Arrivage(ProductId aProductId, StockId aStockId, ArrivageId anArrivageId, Quantity aQuantity, BigDecimal aUnitPrice,
                    String aDescription) {

        this();
        this.setProductId(aProductId);
        this.setStockId(aStockId);
        this.setArrivageId(anArrivageId);
        this.setQuantity(aQuantity);
        this.setUnitPrice(aUnitPrice);
        this.setDescription(aDescription);
        this.setRunOut(false);
        this.setLifeSpanTime(new LifeSpanTime(ZonedDateTime.now()));
    }


    public Arrivage() {
        super();
    }

    /*** Business logic***/
    public void changeUnitPrice(BigDecimal aUnitPrice) {
        if (aUnitPrice == null) {
            throw new IllegalArgumentException("Invalid price");
        }

        if (aUnitPrice.doubleValue() <= 0) {
            throw new IllegalArgumentException("Invalid negative price");
        }

        this.setUnitPrice(aUnitPrice);
    }

    public void changeQuantity(int aValue) {
        if (aValue <= 0) {
            throw new IllegalArgumentException("Invalid negative quantity");
        }

        this.setQuantity(new Quantity(aValue));
        
        
        DomainEventPublisher.instance()
        .publish(
        		new ArrivageQuantityChanged(
        				this.arrivageId,
        				this.stockId(),
        				this.quantity()));
    }

    /*** Getters and Setters ***/
    private void setArrivageId(ArrivageId anArrivageId) {
        this.arrivageId = anArrivageId;
    }

    private void setUnitPrice(BigDecimal aUnitPrice) {
        this.unitPrice = aUnitPrice;
    }

    private void setProductId(ProductId aProductId) {
        this.productId = aProductId;
    }

    private void setQuantity(Quantity aQuantity) {
        this.quantity = aQuantity;
    }

    private void setDescription(String aDescription) {
        this.description = aDescription;
    }

    private void setLifeSpanTime(LifeSpanTime aLifeSpanTime) {
        this.lifeSpanTime = aLifeSpanTime;
    }

    public StockId stockId() {
        return this.stockId;
    }

    public ArrivageId arrivageId() {
        return this.arrivageId;
    }

    public ProductId productId() {
        return this.productId;
    }

    public Quantity quantity() {
        return this.quantity;
    }

    public BigDecimal uniPrice() {

        return this.unitPrice;
    }

    public String description() {
        return this.description;
    }

    public LifeSpanTime lifeSpanTime() {
        return this.lifeSpanTime;
    }

    public ZonedDateTime dateStockCleared() {
        return this.lifeSpanTime().endDate();
    }

    public BigDecimal unitPrice() {
        return this.unitPrice;
    }

    public boolean isrunOut() {
        return this.isRunOut;
    }

    private void setRunOut(boolean runOut) {
        this.isRunOut = runOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arrivage arrivage = (Arrivage) o;

        return arrivageId != null ? arrivageId.equals(arrivage.arrivageId) : arrivage.arrivageId == null;
    }

    @Override
    public int hashCode() {
        return arrivageId != null ? arrivageId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "\n Arrivage{" +
                "arrivageId=" + arrivageId +
                ", stockId=" + stockId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", description='" + description + '\'' +
                ", lifeSpanTime=" + lifeSpanTime +
                '}';
    }

    private void setStockId(StockId stockId) {

        if (this.stockId() == null)
            this.stockId = stockId;
    }


}

package com.dddtraining.inventory.domain.model.stock;

import java.time.ZonedDateTime;
import java.util.Set;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.product.ProductId;

public class StockQuantityChanged implements DomainEvent {



    private StockId stockId;
    private ProductId productId;
    private Quantity quantity;
    private Set<StockProductArrivage> allModifiedArrivages;
    private int eventVersion;
    private ZonedDateTime occurredOn;




    public StockQuantityChanged(StockId stockId, ProductId productId, Quantity quantity, Set<StockProductArrivage> allModifiedArrivages) {
        this.stockId = stockId;
        this.productId = productId;
        this.quantity = quantity;
        this.allModifiedArrivages = allModifiedArrivages;
        this.eventVersion = 1;
        this.occurredOn = ZonedDateTime.now();
    }


    public StockId stockId() {
        return this.stockId;
    }

    public ProductId productId() {
        return this.productId;
    }

    public Quantity quantity() {
        return this.quantity;
    }

    public Set<StockProductArrivage> allModifiedArrivages() {
        return this.allModifiedArrivages;
    }

    public int eventVersion() {
        return this.eventVersion;
    }

    public ZonedDateTime occurredOn() {
        return this.occurredOn;
    }

    @Override
    public String toString() {
        return "StockQuantityChanged{" +
                "stockId=" + stockId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", allModifiedArrivages=" + allModifiedArrivages +
                ", eventVersion=" + eventVersion +
                ", occurredOn=" + occurredOn +
                '}';
    }
}

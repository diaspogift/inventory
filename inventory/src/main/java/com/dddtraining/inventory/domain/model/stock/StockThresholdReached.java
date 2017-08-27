package com.dddtraining.inventory.domain.model.stock;

import java.time.ZonedDateTime;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.product.ProductId;

public class StockThresholdReached implements DomainEvent {

    StockId stockId;
    ProductId productId;
    Quantity quantity;
    int eventVersion;
    private ZonedDateTime occurredOn;


    public StockThresholdReached(StockId stockId, ProductId productId, Quantity quantity) {
        this.stockId = stockId;
        this.productId = productId;
        this.quantity = quantity;
        this.occurredOn = ZonedDateTime.now();
        this.eventVersion = 1;
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

    public int eventVersion() {
        return this.eventVersion;
    }

    public ZonedDateTime occurredOn() {
        return this.occurredOn;
    }


    @Override
    public String toString() {
        return "StockThresholdReached{" +
                "stockId=" + stockId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", occurredOn=" + occurredOn +
                ", eventVersion=" + eventVersion +
                '}';
    }
}

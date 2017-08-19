package com.dddtraining.inventory.domain.model.stock;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.product.ProductId;

import java.time.ZonedDateTime;

public class StockThresholdReached implements DomainEvent {

    StockId stockId;
    ProductId productId;
    Quantity quantity;
    private ZonedDateTime occurredOn;
    int eventVersion;


    public StockThresholdReached(StockId stockId, ProductId productId, Quantity quantity) {
        this.stockId = stockId;
        this.productId = productId;
        this.quantity = quantity;
        this.occurredOn = ZonedDateTime.now();
        this.eventVersion = 1;
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

package com.dddtraining.inventory.domain.model.stock;

import java.time.ZonedDateTime;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.product.ProductId;

public class StockEmptied implements DomainEvent {

    StockId stockId;
    ProductId productId;
    Quantity quantity;
    int eventVersion;
    private ZonedDateTime occurredOn;


    public StockEmptied(StockId stockId, ProductId productId, Quantity quantity) {
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

    public StockId stockId() {
        return this.stockId;
    }

    public ProductId productId() {
        return this.productId;
    }

    public Quantity quantity() {
        return this.quantity;
    }

    @Override
    public String toString() {
        return "StockEmptied{" +
                "stockId=" + stockId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", occurredOn=" + occurredOn +
                ", eventVersion=" + eventVersion +
                '}';
    }
}

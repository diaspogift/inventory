package com.dddtraining.inventory.domain.model.stock;

import java.time.ZonedDateTime;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.product.ProductId;

public class StockCreated implements DomainEvent {


    StockId stockId;
    ProductId productId;
    Quantity quantity;
    int eventVersion;
    private ZonedDateTime occurredOn;

    public StockCreated(StockId stockId, ProductId productId, Quantity quantity, int threshold) {
        this.stockId = stockId;
        this.productId = productId;
        this.quantity = quantity;
        this.occurredOn = ZonedDateTime.now();
        this.eventVersion = 1;
    }

    public StockId stockId() {
        return stockId;
    }

    public ProductId productId() {
        return productId;
    }

    public Quantity quantity() {
        return quantity;
    }

    public int eventVersion() {
        return this.eventVersion;
    }

    public ZonedDateTime occurredOn() {
        return this.occurredOn;
    }


    @Override
    public String toString() {
        return "StockCreated{" +
                "stockId=" + stockId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", occurredOn=" + occurredOn +
                ", eventVersion=" + eventVersion +
                '}';
    }


}

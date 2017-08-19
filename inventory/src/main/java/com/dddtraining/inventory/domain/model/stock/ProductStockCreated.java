package com.dddtraining.inventory.domain.model.stock;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.product.ProductId;

import java.time.ZonedDateTime;

public class ProductStockCreated implements DomainEvent {

    StockId stockId;
    ProductId productId;
    Quantity quantity;
    private ZonedDateTime occurredOn;
    int eventVersion;


    public ProductStockCreated(StockId stockId, ProductId productId, Quantity quantity) {
        this.stockId = stockId;
        this.productId = productId;
        this.quantity = quantity;
        this.eventVersion = 1;
        this.occurredOn = ZonedDateTime.now();
    }

    public int eventVersion() {
        return this.eventVersion;
    }

    public ZonedDateTime occurredOn() {
        return this.occurredOn;
    }


    @Override
    public String toString() {
        return "ProductStockCreated{" +
                "stockId=" + stockId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", occurredOn=" + occurredOn +
                ", eventVersion=" + eventVersion +
                '}';
    }
}

package com.dddtraining.inventory.domain.model.product;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.stock.StockId;

import java.time.ZonedDateTime;

public class ProductStockAssigned implements DomainEvent {

    private StockId stockId;
    private ProductId productId;
    private ZonedDateTime occurredOn;
    private int eventVersion;

    public ProductStockAssigned(StockId stockId, ProductId productId) {
        this.stockId = stockId;
        this.productId = productId;
        this.eventVersion = 1;
        this.occurredOn = ZonedDateTime.now();
    }


    public StockId stockId() {
        return stockId;
    }

    public ProductId productId() {
        return productId;
    }

    public int eventVersion() {
        return this.eventVersion;
    }

    public ZonedDateTime occurredOn() {
        return this.occurredOn;
    }

    @Override
    public String toString() {
        return "ProductStockAssigned{" +
                "stockId=" + stockId +
                ", productId=" + productId +
                ", occurredOn=" + occurredOn +
                ", eventVersion=" + eventVersion +
                '}';
    }
}

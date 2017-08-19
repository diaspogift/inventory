package com.dddtraining.inventory.domain.model.product;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.StockId;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class ProductCreated implements DomainEvent {

    private ProductId productId;
    private String name;
    private String description;
    private AvailabilityStatus status;
    private ZonedDateTime occurredOn;
    int eventVersion;


    public ProductCreated(ProductId productId, String name, String description, AvailabilityStatus status) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.occurredOn = ZonedDateTime.now();
        this.eventVersion = 1;
    }

    public ProductId productId() {
        return productId;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public AvailabilityStatus status() {
        return status;
    }

    public int eventVersion() {
        return this.eventVersion;
    }

    public ZonedDateTime occurredOn() {
        return this.occurredOn;
    }

    @Override
    public String toString() {
        return " \n\n\n\n ProductCreated{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", occurredOn=" + occurredOn +
                ", eventVersion=" + eventVersion +
                '}'+"  \n\n \n\n";
    }
}

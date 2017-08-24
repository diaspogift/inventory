package com.dddtraining.inventory.domain.model.stock;

import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.common.DomainEvent;

import java.time.ZonedDateTime;

public class ArrivageQuantityDecremented implements DomainEvent{

    private ArrivageId arrivageId;
    private Quantity quantity;
    private  int eventVersion;
    private ZonedDateTime occurredOn;


    public ArrivageQuantityDecremented(ArrivageId arrivageId, Quantity aQuantity) {
        this.arrivageId = arrivageId;
        this.quantity = aQuantity;
        this.eventVersion = 1;
        this.occurredOn = ZonedDateTime.now();
    }


    public ArrivageId arrivageId() {
        return arrivageId;
    }


    public Quantity quantity() {
        return quantity;
    }



    public void setEventVersion(int eventVersion) {
        this.eventVersion = eventVersion;
    }

    public void setOccurredOn(ZonedDateTime occurredOn) {
        this.occurredOn = occurredOn;
    }

    public int eventVersion() {
        return this.eventVersion;
    }

    public ZonedDateTime occurredOn() {
        return this.occurredOn;
    }
}

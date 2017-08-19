package com.dddtraining.inventory.domain.model.product;

import com.dddtraining.inventory.domain.model.common.DomainEvent;

import java.time.ZonedDateTime;

public class AvailabilityStatusChanged implements DomainEvent {



    private ZonedDateTime occurredOn;
    int eventVersion;


    public int eventVersion() {
        return this.eventVersion;
    }

    public ZonedDateTime occurredOn() {
        return this.occurredOn;
    }
}

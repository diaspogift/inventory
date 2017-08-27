package com.dddtraining.inventory.domain.model.product;

import java.time.ZonedDateTime;

import com.dddtraining.inventory.domain.model.common.DomainEvent;

public class AvailabilityStatusChanged implements DomainEvent {


    int eventVersion;
    private ZonedDateTime occurredOn;

    public int eventVersion() {
        return this.eventVersion;
    }

    public ZonedDateTime occurredOn() {
        return this.occurredOn;
    }
}

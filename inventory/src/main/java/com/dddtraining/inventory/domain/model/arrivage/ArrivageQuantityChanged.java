package com.dddtraining.inventory.domain.model.arrivage;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.stock.Quantity;

import java.time.ZonedDateTime;

public class ArrivageQuantityChanged implements DomainEvent {

    private ArrivageId arrivageId;
    private Quantity quantity;
    private int eventVersion;
    private ZonedDateTime occurredOn;


    public ArrivageQuantityChanged(ArrivageId arrivageId, Quantity aQuantity) {
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


	
    
    @Override
	public String toString() {
		return "ArrivageQuantityChanged [arrivageId=" + arrivageId + ", quantity=" + quantity + ", eventVersion="
				+ eventVersion + ", occurredOn=" + occurredOn + "]";
	}
    
    
    
}

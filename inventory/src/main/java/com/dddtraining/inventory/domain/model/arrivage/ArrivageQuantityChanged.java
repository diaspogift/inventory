package com.dddtraining.inventory.domain.model.arrivage;

import java.time.ZonedDateTime;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.StockId;

public class ArrivageQuantityChanged implements DomainEvent {

    private ArrivageId arrivageId;
    private StockId stockId;
    private Quantity quantity;
    private int eventVersion;
    private ZonedDateTime occurredOn;


    public ArrivageQuantityChanged(ArrivageId arrivageId, StockId stockId, Quantity aQuantity) {
        this.arrivageId = arrivageId;
        this.stockId = stockId;
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

	public StockId stockId() {
		return this.stockId;
	}


	@Override
	public String toString() {
		return "ArrivageQuantityChanged [arrivageId=" + arrivageId + ", stockId=" + stockId + ", quantity=" + quantity
				+ ", eventVersion=" + eventVersion + ", occurredOn=" + occurredOn + "]";
	}
    
    
    
}

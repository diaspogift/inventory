package com.dddtraining.inventory.domain.model.arrivage;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.dddtraining.inventory.domain.model.common.DomainEvent;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.LifeSpanTime;
import com.dddtraining.inventory.domain.model.stock.Quantity;
import com.dddtraining.inventory.domain.model.stock.StockId;

public class NewArrivageCreated implements DomainEvent {


	private ArrivageId arrivageId;
	private ProductId productId;
	private Quantity quantity;
	private BigDecimal unitPrice;
	private String description;
	private LifeSpanTime lifeSpanTime;
	private ZonedDateTime occurredOn;
	int eventVersion;

	public NewArrivageCreated( ProductId productId, ArrivageId arrivageId, Quantity quantity, BigDecimal unitPrice, String description, LifeSpanTime lifeSpanTime) {
		this.arrivageId = arrivageId;
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.description = description;
		this.lifeSpanTime = lifeSpanTime;
		this.occurredOn = ZonedDateTime.now();
		this.eventVersion = 1;
	}

	public ArrivageId arrivageId() {
		return arrivageId;
	}

	public ProductId productId() {
		return productId;
	}

	public Quantity quantity() {
		return quantity;
	}

	public BigDecimal unitPrice() {
		return unitPrice;
	}

	public String description() {
		return description;
	}

	public LifeSpanTime lifeSpanTime() {
		return lifeSpanTime;
	}

	public int eventVersion() {
		return this.eventVersion;
	}

	public ZonedDateTime occurredOn() {
		return this.occurredOn;
	}



	@Override
	public String toString() {
		return "\n\n\n\nNewArrivageCreated{" +
				"arrivageId=" + arrivageId +
				", productId=" + productId +
				", quantity=" + quantity +
				", unitPrice=" + unitPrice +
				", description='" + description + '\'' +
				", lifeSpanTime=" + lifeSpanTime +
				", occurredOn=" + occurredOn +
				", eventVersion=" + eventVersion +
				'}' + "\n\n\n\n\n";
	}
}

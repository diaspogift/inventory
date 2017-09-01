package com.dddtraining.inventory.port.adapter.messaging.ativemq;

import com.dddtraining.inventory.domain.model.stock.StockProductArrivage;

public class JsonStockProductArrivageRep {
	
    private String arrivageId;
    private String productId;
    private String  lifeSpanTimeStartDate, lifeSpanTimeEndDate;
    private int ordering;
    private int quantity;

	public JsonStockProductArrivageRep(StockProductArrivage next) {
		
		this.arrivageId = next.arrivageId().id();
		this.productId = next.productId().id();
		this.lifeSpanTimeStartDate = String.valueOf(next.lifeSpanTime().startDate());
		this.lifeSpanTimeEndDate= String.valueOf(next.lifeSpanTime().endDate());
		this.ordering = next.ordering();
		this.quantity = next.quantity().value();

	}
	
	

	public JsonStockProductArrivageRep(String arrivageId, String productId, String lifeSpanTimeStartDate,
			String lifeSpanTimeEndDate, int ordering, int quantity) {
		super();
		this.arrivageId = arrivageId;
		this.productId = productId;
		this.lifeSpanTimeStartDate = lifeSpanTimeStartDate;
		this.lifeSpanTimeEndDate = lifeSpanTimeEndDate;
		this.ordering = ordering;
		this.quantity = quantity;
	}



	public String getArrivageId() {
		return arrivageId;
	}

	public void setArrivageId(String arrivageId) {
		this.arrivageId = arrivageId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLifeSpanTimeStartDate() {
		return lifeSpanTimeStartDate;
	}

	public void setLifeSpanTimeStartDate(String lifeSpanTimeStartDate) {
		this.lifeSpanTimeStartDate = lifeSpanTimeStartDate;
	}

	public String getLifeSpanTimeEndDate() {
		return lifeSpanTimeEndDate;
	}

	public void setLifeSpanTimeEndDate(String lifeSpanTimeEndDate) {
		this.lifeSpanTimeEndDate = lifeSpanTimeEndDate;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	@Override
	public String toString() {
		return "JsonStockProductArrivageRep [arrivageId=" + arrivageId + ", productId=" + productId
				+ ", lifeSpanTimeStartDate=" + lifeSpanTimeStartDate + ", lifeSpanTimeEndDate=" + lifeSpanTimeEndDate
				+ ", ordering=" + ordering + ", quantity=" + quantity + "]";
	}

	
	
}

package com.dddtraining.inventory.domain.model.stock;

import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.product.ProductId;

public class StockProductArrivage {
	
	public StockProductArrivage(ProductId aProductId, ArrivageId anArrivageId, LifeSpanTime aLifeSpanTime) {

		this.setProductId(aProductId);
		this.setArrivageId(anArrivageId);
		this.setLifeSpanTime(aLifeSpanTime);

	}
	
	private ArrivageId arrivageId;
	private ProductId productId;
	private LifeSpanTime lifeSpanTime;


	public ArrivageId arrivageId() {
		return arrivageId;
	}

	private void setArrivageId(ArrivageId arrivageId) {
		this.arrivageId = arrivageId;
	}

	public ProductId productId() {
		return productId;
	}

	private void setProductId(ProductId productId) {
		this.productId = productId;
	}

	public LifeSpanTime lifeSpanTime() {
		return lifeSpanTime;
	}

	private void setLifeSpanTime(LifeSpanTime lifeSpanTime) {
		this.lifeSpanTime = lifeSpanTime;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StockProductArrivage that = (StockProductArrivage) o;

		if (arrivageId != null ? !arrivageId.equals(that.arrivageId) : that.arrivageId != null) return false;
		if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
		return lifeSpanTime != null ? lifeSpanTime.equals(that.lifeSpanTime) : that.lifeSpanTime == null;
	}

	@Override
	public int hashCode() {
		int result = arrivageId != null ? arrivageId.hashCode() : 0;
		result = 31 * result + (productId != null ? productId.hashCode() : 0);
		result = 31 * result + (lifeSpanTime != null ? lifeSpanTime.hashCode() : 0);
		return result;
	}

	public StockProductArrivage() {
		super();
	}


	@Override
	public String toString() {
		return "StockProductArrivage{" +
				"arrivageId=" + arrivageId +
				", productId=" + productId +
				", lifeSpanTime=" + lifeSpanTime +
				'}';
	}
}

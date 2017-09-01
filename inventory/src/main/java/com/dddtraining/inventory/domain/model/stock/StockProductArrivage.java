package com.dddtraining.inventory.domain.model.stock;

import java.io.Serializable;

import javax.persistence.*;

import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.LifeSpanTime;
import com.dddtraining.inventory.domain.model.common.ConcurrencySafeEntity;
import com.dddtraining.inventory.domain.model.product.ProductId;

//Trully a value object to be improved
@Entity
public class StockProductArrivage extends ConcurrencySafeEntity{

    @Embedded
    private ArrivageId arrivageId;
	@Embedded
    private ProductId productId;
	@Embedded
    private LifeSpanTime lifeSpanTime;
    private int ordering;
	@Embedded
    private Quantity quantity;



    public StockProductArrivage(ProductId aProductId, ArrivageId anArrivageId, LifeSpanTime aLifeSpanTime, Quantity quantity, int anOrdering) {

        this.setProductId(aProductId);
        this.setArrivageId(anArrivageId);
        this.setLifeSpanTime(aLifeSpanTime);
        this.setQuantity(quantity);
        this.setOrdering(anOrdering);

    }

    
    public StockProductArrivage(ArrivageId anArrivageId, Quantity aQuantity) {

        this.setArrivageId(anArrivageId);
        this.setQuantity(aQuantity);
    }

    //Business logic




	public void reorderFrom(ArrivageId anArrivageId, int anOrdering) {
        if (this.arrivageId().equals(anArrivageId)) {
            this.setOrdering(anOrdering);
        } else if (this.ordering() >= anOrdering) {
            this.setOrdering(this.ordering() + 1);
        }

    }


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

    public int ordering() {

        return this.ordering;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockProductArrivage that = (StockProductArrivage) o;

        if (ordering != that.ordering) return false;
        if (arrivageId != null ? !arrivageId.equals(that.arrivageId) : that.arrivageId != null) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        return lifeSpanTime != null ? lifeSpanTime.equals(that.lifeSpanTime) : that.lifeSpanTime == null;
    }

    @Override
    public int hashCode() {
        int result = arrivageId != null ? arrivageId.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (lifeSpanTime != null ? lifeSpanTime.hashCode() : 0);
        result = 31 * result + ordering;
        return result;
    }


    @Override
    public String toString() {
        return "StockProductArrivage{" +
                "arrivageId=" + arrivageId +
                ", productId=" + productId +
                ", lifeSpanTime=" + lifeSpanTime +
                ", ordering=" + ordering +
                ", quantity=" + quantity +
                ", hibernate id =" + this.id() +
                '}';
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }


    public Quantity quantity() {

        return this.quantity;
    }

    private void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public void resetQuantity(Quantity aQuantity) {
        if (aQuantity.value() < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }
        this.setQuantity(aQuantity);
    }


	public StockProductArrivage createFrom(StockProductArrivage aStockProductArrivage) {


        System.out.println("\n\nIn Create From aStockProductArrivage = "+aStockProductArrivage.toString());
        System.out.println("\n\nIn Create From this  = "+this.toString());

		return new StockProductArrivage(
				this.productId(),
				aStockProductArrivage.arrivageId(),
				this.lifeSpanTime(),
				aStockProductArrivage.quantity(),
				this.ordering());
	}


	public StockProductArrivage() {
		super();
	}




}

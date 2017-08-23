package com.dddtraining.inventory.domain.model.stock;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.NewArrivageCreated;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.product.ProductId;


public class Stock {

	private StockId stockId;
	private ProductId productId;
	private Quantity quantity;
	private int threshold;
    private ZonedDateTime dateStockThresholdReached;
    private boolean availability;
    private Set<StockProductArrivage> stockProductArrivages;
    private  boolean isThesholdReached;




	public Stock(StockId aStockId, ProductId aProductId) {

		super();
		this.setStockId(aStockId);
		this.setProductId(aProductId);
		this.setQuantity(new Quantity(0));
		this.setAvailability(true);
		this.setThesholdReached(false);
		this.setStockProductArrivages(new HashSet<StockProductArrivage>());
	}

    public Stock(StockId aStockId, ProductId aProductId, Quantity aQuantity) {

        super();
        this.setStockId(aStockId);
        this.setProductId(aProductId);
        this.setQuantity(aQuantity);
        this.setAvailability(true);
        this.setStockProductArrivages(new HashSet<StockProductArrivage>());


        //StockCreated domain event


		DomainEventPublisher.instance()
				.publish(new StockCreated(
						this.stockId(),
						this.productId(),
						this.quantity()));
    }

	public Stock(StockId aStockId, ProductId aProductId, Quantity aQuantity, int aThreshold) {


		super();
		this.setStockId(aStockId);
		this.setProductId(aProductId);
		this.setQuantity(aQuantity);
		this.setThreshold(aThreshold);
		this.setThesholdReached(false);
		this.setAvailability(true);
        this.setStockProductArrivages(new HashSet<StockProductArrivage>());


        //StockCreated domain event


		DomainEventPublisher.instance()
				.publish(new StockCreated(
						this.stockId(),
						this.productId(),
						this.quantity()));
	}

	/*** Business logic ***/
	public void augmentStockOf(int aValue) {

		this.setQuantity(this.quantity().increment(aValue));
	}

	public void clearStockOf(int aQuantityToClear) {

	    Quantity actualQuantity = this.quantity();

		this.setQuantity(this.quantity().decrement(aQuantityToClear));

		// Raise Domain event
		if(this.isThresholdReached(actualQuantity, new Quantity(aQuantityToClear))){

            this.setThesholdReached(true);

			DomainEventPublisher.instance().publish(
					new StockThresholdReached(
							this.stockId(),
							this.productId(),
							this.quantity()));

		}

		if(this.isStockEmpty()){

			this.unAvailable();

			DomainEventPublisher.instance().publish(
					new StockEmptied(
							this.stockId(),
							this.productId(),
							this.quantity()));
		}

	}

	private boolean isThresholdReached(Quantity actualQuantity, Quantity aQuantityToClear){
		return ( actualQuantity.decrement(aQuantityToClear.value()).value() <=  this.threshold());
	}

	private boolean isStockEmpty(){
		return (this.quantity().value() == 0);
	}


	public void adjustThreshold(int aThreshold){
		this.setThreshold(aThreshold);
	}




	public boolean isAvailable() {
		return this.availability;
	}

	public void unAvailable() {
		if (this.isAvailable()){
			this.setAvailability(false);
		}
	}


    public void thresholdReachedOn(ZonedDateTime aDateStockThresholdReached){

        if(aDateStockThresholdReached == null){
            throw new IllegalArgumentException("Invalid threshold date!");
        }
        if(aDateStockThresholdReached.isAfter(ZonedDateTime.now())){
            throw new IllegalArgumentException("Invalid threshold date!");
        }
        this.setDateThresholdReached(aDateStockThresholdReached);
    }

    public void addNewStockProductArrivage(Arrivage anArrivage){

        if(anArrivage == null ){
            throw new IllegalArgumentException("Invalid arrivage");
        }

        StockProductArrivage aStockProductArrivage =
                new StockProductArrivage(
                        this.productId(),
                        anArrivage.arrivageId(),
                        anArrivage.lifeSpanTime());


        this.stockProductArrivages().add(aStockProductArrivage);
        this.setQuantity(this.quantity().increment(anArrivage.quantity()));
    }


    /*** Getters and Setters ***/
	private void setStockId(StockId aStockId) {
		this.stockId = aStockId;
	}

	private void setProductId(ProductId aProductId) {
		this.productId = aProductId;
	}

	private void setQuantity(Quantity aQuantity) {
		this.quantity = aQuantity;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	private void setAvailability(boolean anActive) {
		this.availability = anActive;
	}


	public StockId stockId() {
		return this.stockId;
	}

	public ProductId productId() {
		return this.productId;
	}

	public Quantity quantity() {
		return this.quantity;
	}
	public int threshold() {
		return this.threshold;
	}
    public ZonedDateTime dateStockThresholdReached() {
        return this.dateStockThresholdReached;
    }

    private void setDateThresholdReached(ZonedDateTime aDateThresholdReached) {
        this.dateStockThresholdReached = aDateThresholdReached;
    }
    public Set<StockProductArrivage> stockProductArrivages() {
        return this.stockProductArrivages;
    }

    private void setStockProductArrivages(Set<StockProductArrivage> stockProductArrivages) {
        this.stockProductArrivages = stockProductArrivages;
    }
	public boolean isthesholdReached() {
		return isThesholdReached;
	}

	private void setThesholdReached(boolean thesholdReached) {
		isThesholdReached = thesholdReached;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stockId == null) ? 0 : stockId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (stockId == null) {
			if (other.stockId != null)
				return false;
		} else if (!stockId.equals(other.stockId))
			return false;
		return true;
	}

	public Stock() {
		super();
	}


	@Override
	public String toString() {
		return "Stock{" +
				"stockId=" + stockId +
				", productId=" + productId +
				", quantity=" + quantity +
				", threshold=" + threshold +
				", dateStockThresholdReached=" + dateStockThresholdReached +
				", availability=" + availability +
				", stockProductArrivages=" + stockProductArrivages +
				", isThesholdReached=" + isThesholdReached +
				'}';
	}
}

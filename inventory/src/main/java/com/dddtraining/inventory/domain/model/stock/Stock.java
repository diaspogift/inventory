package com.dddtraining.inventory.domain.model.stock;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dddtraining.inventory.InventoryApplication;
import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.product.ProductId;


@Entity
public class Stock {

    private static final Logger logger = LoggerFactory
            .getLogger(InventoryApplication.class);

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Embedded
    private StockId stockId;
    @Embedded
    private ProductId productId;
    @Embedded
    private Quantity quantity;
    private int threshold;
    private ZonedDateTime dateStockThresholdReached;
    private boolean availability;
    @OneToMany(cascade=CascadeType.PERSIST)
    private Set<StockProductArrivage> stockProductArrivages;
    private boolean isThesholdReached;


    public Stock(StockId aStockId, ProductId aProductId) {

        super();
        this.setStockId(aStockId);
        this.setProductId(aProductId);
        this.setQuantity(new Quantity(0));
        this.setAvailability(false);
        this.setThesholdReached(false);
        this.setStockProductArrivages(new HashSet<StockProductArrivage>());

        //StockCreated domain event
        DomainEventPublisher.instance()
                .publish(new StockCreated(
                        this.stockId(),
                        this.productId(),
                        this.quantity(),
                        this.threshold()));
    }

    public Stock(StockId aStockId, ProductId aProductId, Quantity aQuantity) {

        super();
        this.setStockId(aStockId);
        this.setProductId(aProductId);
        this.setQuantity(aQuantity);
        this.setAvailability(false);
        this.setStockProductArrivages(new HashSet<StockProductArrivage>());


        //StockCreated domain event
        DomainEventPublisher.instance()
                .publish(new StockCreated(
                        this.stockId(),
                        this.productId(),
                        this.quantity(),
                        this.threshold()));
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
                        this.quantity(),
                        this.threshold()));
    }

    public Stock() {
        super();
    }

    /*** Business logic ***/

    public void clearStockOf(int aQuantityToClear) {



        Quantity actualQuantity = this.quantity();

        this.setQuantity(this.quantity().decrement(aQuantityToClear));
        Set<StockProductArrivage> allModifiedArrivages =  this.decrementAStockArrivage(this, new Quantity(aQuantityToClear), 1, new HashSet<StockProductArrivage>());

        DomainEventPublisher.instance().publish(
                new StockQuantityChanged(
                        this.stockId(),
                        this.productId(),
                        this.quantity(),
                        allModifiedArrivages)
        );




        if (this.isThresholdReached(actualQuantity, new Quantity(aQuantityToClear))) {

            this.setThesholdReached(true);


            DomainEventPublisher.instance().publish(
                    new StockThresholdReached(
                            this.stockId(),
                            this.productId(),
                            this.quantity()));

        }

        if (this.isStockEmpty()) {

            this.unAvailable();

            DomainEventPublisher.instance().publish(
                    new StockEmptied(
                            this.stockId(),
                            this.productId(),
                            this.quantity()));
        }

    }

    private boolean isThresholdReached(Quantity actualQuantity, Quantity aQuantityToClear) {

        boolean before = actualQuantity.value() > this.threshold();

        return before && (actualQuantity.decrement(aQuantityToClear.value()).value() <= this.threshold());
    }

    private boolean isStockEmpty() {
        return (this.quantity().value() == 0);
    }

    public void adjustThreshold(int aThreshold) {
        this.setThreshold(aThreshold);
    }

    public boolean isAvailable() {
        return this.availability;
    }

    public void unAvailable() {
        if (this.isAvailable()) {
            this.setAvailability(false);
        }
    }

    public void thresholdReachedOn(ZonedDateTime aDateStockThresholdReached) {

        if (aDateStockThresholdReached == null) {
            throw new IllegalArgumentException("Invalid threshold date!");
        }
        if (aDateStockThresholdReached.isAfter(ZonedDateTime.now())) {
            throw new IllegalArgumentException("Invalid threshold date!");
        }
        this.setDateThresholdReached(aDateStockThresholdReached);
    }

    public void addNewStockProductArrivage(Arrivage anArrivage) {

        if (anArrivage == null) {
            throw new IllegalArgumentException("Invalid arrivage");
        }

        int ordering = this.stockProductArrivages().size() + 1;

        StockProductArrivage aStockProductArrivage =
                new StockProductArrivage(
                        this.productId(),
                        anArrivage.arrivageId(),
                        anArrivage.lifeSpanTime(),
                        anArrivage.quantity(),
                        ordering);


        this.stockProductArrivages().add(aStockProductArrivage);
        this.setQuantity(this.quantity().increment(anArrivage.quantity()));
    }
    
    private void addNewStockProductArrivage(StockProductArrivage aStockProductArrivage) {

        if (aStockProductArrivage == null) {
            throw new IllegalArgumentException("Invalid arrivage");
        }

        this.stockProductArrivages().add(aStockProductArrivage);
        this.setQuantity(this.quantity().increment(aStockProductArrivage.quantity()));
    }

    public void reorderFrom(ArrivageId anArrivageId, int anOrdering) {

        for (StockProductArrivage nextStockProductArrivage : this.stockProductArrivages()) {
            nextStockProductArrivage.reorderFrom(anArrivageId, anOrdering);
        }
    }

    private Set<StockProductArrivage> decrementAStockArrivage(Stock aStock, Quantity aQuantity, int position, Set<StockProductArrivage> initStockProductArrivages) {


        if(quantity.value() == 0)
            return initStockProductArrivages;

        for (StockProductArrivage nextStockProductArrivage : aStock.stockProductArrivages()) {

            if (nextStockProductArrivage.ordering() == position) {

                if (nextStockProductArrivage.quantity().value() >= aQuantity.value()) {

                    nextStockProductArrivage.resetQuantity(nextStockProductArrivage.quantity().decrement(aQuantity.value()));
                    initStockProductArrivages.add(nextStockProductArrivage);

                    return initStockProductArrivages;

                } else if (nextStockProductArrivage.quantity().value() < aQuantity.value()) {

                    Quantity residuelQuantity =
                            aQuantity.decrement(nextStockProductArrivage.quantity().value());

                    nextStockProductArrivage.resetQuantity(new Quantity(0));
                    initStockProductArrivages.add(nextStockProductArrivage);


                    logger.debug("\n\n");
                    logger.debug("\n PASSING THIS QUANTITY IN DECREMENT STOCK QUANTITY " + residuelQuantity);
                    logger.debug("\n\n");

                    initStockProductArrivages.forEach(s-> logger.info(s.toString()));



                    initStockProductArrivages = decrementAStockArrivage(aStock, residuelQuantity, position + 1, initStockProductArrivages);

                }
            }
        }

        return  initStockProductArrivages;

    }
    
    
    //TO Refactor
    public void updateProductArrivage(StockProductArrivage aStockProductArrivage) {
    	
    	  
    	
    	StockProductArrivage aNewStockProductArrivage = null;
    	StockProductArrivage anOldStockProductArrivage = null;

    	for(StockProductArrivage next : this.stockProductArrivages()){
    		
    		if (next.arrivageId().equals(aStockProductArrivage.arrivageId())){
    			
    			anOldStockProductArrivage = next;
    			
    			aNewStockProductArrivage = next.createFrom(aStockProductArrivage);
    		

    			this.setQuantity(this.quantity().decrement(next.quantity().value()));
    			
    			

    		}
    	}
    	

    	
		this.stockProductArrivages().remove(anOldStockProductArrivage);
		this.addNewStockProductArrivage(aNewStockProductArrivage);


    	
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

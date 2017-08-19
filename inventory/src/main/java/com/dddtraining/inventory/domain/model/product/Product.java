package com.dddtraining.inventory.domain.model.product;

import java.math.BigDecimal;

import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.NewArrivageCreated;
import com.dddtraining.inventory.domain.model.common.DomainEventPublisher;
import com.dddtraining.inventory.domain.model.stock.*;

public class Product {

	private ProductId productId;
	private StockId stockId;
	private String name;
	private String description;
	private AvailabilityStatus status;


    public Product(ProductId aProductId, String aName, String aDescription) {

		this();
		this.setProductId(aProductId);
		this.setName(aName);
		this.setDescription(aDescription);
		this.setStatus(AvailabilityStatus.CREATED);

		//ProductCreated domain event

        DomainEventPublisher
                .instance()
                .publish(new ProductCreated(
                        this.productId(),
                        this.name(),
                        this.description(),
                        AvailabilityStatus.CREATED
                ));
	}



	//*** Business logic ***//	

	public void changeAvailabilityStatus(AvailabilityStatus aStatus){
		if(this.status() != aStatus){
			this.setStatus(aStatus);
		}
	}

	public Arrivage createNewArrivage(ArrivageId anArrivageId, Quantity aQuantity, BigDecimal aUnitPrice, String aDescription){
		
		Arrivage arrivage = 
				new Arrivage(
						this.productId(),
					    anArrivageId, 
						aQuantity,
						aUnitPrice, 
						aDescription);


		//NewArrivageCreated domain event

        DomainEventPublisher
                .instance()
                .publish(new NewArrivageCreated(
                        this.productId(),
                        anArrivageId,
                        aQuantity,
                        aUnitPrice,
                        aDescription,
                        arrivage.lifeSpanTime()
                ));
		
		
		return arrivage;
	}


	
	public Stock createStock(StockId aStockId){
		
		Stock stock = 
				new Stock(
						aStockId,
						this.productId()
					   );
		
		this.setStockId(aStockId);


		DomainEventPublisher.instance().publish(
				new ProductStockCreated(
						aStockId,
						this.productId(),
						new Quantity()));

		return stock;
	}



	//*** Getters and Setters ***//
	private void setProductId(ProductId aProductId) {

		if (aProductId == null) {
			throw new IllegalArgumentException("Invalid ProductId provided");
		}
		this.productId = aProductId;
	}
	
	private void setStockId(StockId aStockId) {
		
		if (aStockId == null) {
			throw new IllegalArgumentException("Invalid StockId provided");
		}
		this.stockId = aStockId;
		
	}

	private void setName(String aName) {
		this.name = aName;
	}



	
	private void setStatus(AvailabilityStatus aStatus) {
		this.status = aStatus;
	}

	private void setDescription(String aDescription) {
		this.description = aDescription;
	}


	public ProductId productId() {
		return productId;
	}
	
	public StockId stockId() {
		return this.stockId;
	}

	public String name() {
		return name;
	}



	public AvailabilityStatus status() {
		return this.status;
	}
	



	public String description() {
		return description;
	}





    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		Product other = (Product) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	public Product() {
		super();


	}







}

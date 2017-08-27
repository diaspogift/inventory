package com.dddtraining.inventory.application.command;

public class AdjustStockArrivageQuantityCommand {

	private String arrivageId;
	private String stockId;
	private int quantity;

	public AdjustStockArrivageQuantityCommand(String arrivageId, String stockId, int quantity) {
		this.arrivageId = arrivageId;
		this.stockId = stockId;
		this.quantity = quantity;
		
	}

	public String arrivageId() {
		return arrivageId;
	}

	public String stockId() {
		return this.stockId;
	}

	public int quantity() {
		return this.quantity;
	}

	@Override
	public String toString() {
		return "AdjustStockArrivageQuantityCommand [arrivageId=" + arrivageId + ", stockId=" + stockId + ", quantity="
				+ quantity + "]";
	}

	
	
	
	

}

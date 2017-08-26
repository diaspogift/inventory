package com.dddtraining.inventory.application.command;

public class ChangeArrivageQuantityCommand {
	
	private String arrivageId;
	private int quantity;
	
	
	public ChangeArrivageQuantityCommand(String arrivageId, int quantity) {
		super();
		this.arrivageId = arrivageId;
		this.quantity = quantity;
	}


	public String arrivageId() {
		return this.arrivageId;
	}


	public int quantity() {
		return this.quantity;
	}
	
	
}

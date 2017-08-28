package com.dddtraining.inventory.application.command;

public class RegisterProductCommand {


    private String productId;
    private String name;
    private String description;

    public RegisterProductCommand(String productId, String name, String description) {
        this.productId = productId;
        this.name = name;
        this.description = description;
    }

    public String productId() {
        return this.productId;
    }

    public String name() {

        return this.name;
    }

    public String description() {

        return this.description;
    }

	@Override
	public String toString() {
		return "RegisterProductCommand [productId=" + productId + ", name=" + name + ", description=" + description
				+ "]";
	}


}

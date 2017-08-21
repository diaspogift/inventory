package com.dddtraining.inventory.application.command;

public class DecrementProductStockCommand {


    private int quantity;
    private String productId;

    public DecrementProductStockCommand(int quantity, String productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public int quantity() {
        return this.quantity;
    }

    public String productId() {
        return this.productId;
    }
}

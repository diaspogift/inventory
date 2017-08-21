package com.dddtraining.inventory.application.command;

public class AugmentProductStockCommand {


    private String productId;
    private int quantity;

    public AugmentProductStockCommand(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String productId() {
        return this.productId;
    }

    public int quantity() {
        return this.quantity;
    }
}

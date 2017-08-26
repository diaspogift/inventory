package com.dddtraining.inventory.application.command;

public class DecrementProductStockCommand {


    private int quantity;
    private String productId;
    private String stockId;

    public DecrementProductStockCommand(int quantity, String productId, String stockId) {
        this.quantity = quantity;
        this.productId = productId;
        this.stockId = stockId;
    }

    public int quantity() {
        return this.quantity;
    }

    public String productId() {
        return this.productId;
    }

    public String stockId() {

        return this.stockId;
    }
}

package com.dddtraining.inventory.application.command;

public class CreateStockCommand {

    private String stockId;
    private String productId;
    private int quantity;


    public CreateStockCommand(String stockId, String productId, int quantity) {
        this.stockId = stockId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String stockId() {

        return this.stockId;
    }

    public String productId() {
        return this.productId;
    }

    public int quantity() {
        return this.quantity;
    }
}

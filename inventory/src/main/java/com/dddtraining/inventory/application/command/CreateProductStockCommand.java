package com.dddtraining.inventory.application.command;

public class CreateProductStockCommand {

    private String stockId;
    private String productId;
    private int treshold;

    public CreateProductStockCommand(String stockId, String productId, int treshold) {
        this.stockId = stockId;
        this.productId = productId;
        this.treshold = treshold;
    }

    public String stockId() {

        return this.stockId;
    }

    public int theshold() {
        return this.treshold;
    }

    public String productId() {
        return this.productId;
    }
}

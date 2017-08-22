package com.dddtraining.inventory.application.command;

public class AssignedStockCommand {


    private String productId;
    private String stockId;


    public AssignedStockCommand(String productId, String stockId) {
        this.productId = productId;
        this.stockId = stockId;
    }

    public String productId() {

        return this.productId;
    }

    public String stockId() {

        return this.stockId;
    }
}

package com.dddtraining.inventory.application.command;

import java.math.BigDecimal;

public class RegisterNewStockProductArrivageCommand {

    private String stockId;
    private String description;
    private BigDecimal unitPrice;
    private int quantity;
    private String arrivageId;
    private String productId;


    public RegisterNewStockProductArrivageCommand(String stockId, String description, BigDecimal unitPrice, int quantity, String arrivageId, String productId) {

        this.stockId = stockId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.arrivageId = arrivageId;
        this.productId = productId;
    }


    public String stockId() {
        return this.stockId;
    }

    public String description() {
        return this.description;
    }

    public BigDecimal unitPrice() {

        return this.unitPrice;
    }

    public int quantity() {
        return this.quantity;
    }

    public String arrivageId() {
        return this.arrivageId;
    }

    public String productId() {
        return this.productId;
    }


    @Override
    public String toString() {
        return "RegisterNewStockProductArrivageCommand{" +
                "stockId='" + stockId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", arrivageId='" + arrivageId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}

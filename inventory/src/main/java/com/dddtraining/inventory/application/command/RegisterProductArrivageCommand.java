package com.dddtraining.inventory.application.command;

import java.math.BigDecimal;

public class RegisterProductArrivageCommand {

    private String productId;
    private String arrivageId;
    private int quantity;
    private BigDecimal unitPrice;
    private String description;


    public RegisterProductArrivageCommand(String productId, String arrivageId, int quantity, BigDecimal unitPrice, String description) {
        this.productId = productId;
        this.arrivageId = arrivageId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    public String productId() {
        return this.productId;
    }

    public String arrivageId() {
        return this.arrivageId;
    }

    public int quantity() {
       return  this.quantity;
    }

    public BigDecimal unitPrice() {
        return this.unitPrice;
    }

    public String description() {
        return this.description;
    }


    @Override
    public String toString() {
        return "RegisterProductArrivageCommand{" +
                "productId='" + productId + '\'' +
                ", arrivageId='" + arrivageId + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", description='" + description + '\'' +
                '}';
    }
}

package com.dddtraining.inventory.application.command;

import java.math.BigDecimal;

public class RegisterProductArrivageCommand {

    private String productId;
    private String arrivageId;
    private int quantity;
    private BigDecimal unitPrice;
    private String description;


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
}

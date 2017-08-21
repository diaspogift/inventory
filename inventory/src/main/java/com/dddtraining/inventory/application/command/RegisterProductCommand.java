package com.dddtraining.inventory.application.command;

import com.dddtraining.inventory.domain.model.product.AvailabilityStatus;

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


}

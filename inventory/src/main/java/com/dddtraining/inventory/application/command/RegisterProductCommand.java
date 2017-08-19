package com.dddtraining.inventory.application.command;

import com.dddtraining.inventory.domain.model.product.AvailabilityStatus;

public class RegisterProductCommand {


    private String productId;
    private String name;
    private String description;

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

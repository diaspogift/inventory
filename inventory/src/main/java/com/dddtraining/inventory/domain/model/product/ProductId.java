package com.dddtraining.inventory.domain.model.product;

import com.dddtraining.inventory.domain.model.common.AbstractId;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
public class ProductId extends AbstractId{



    public ProductId(String anId) {
       super(anId);
    }

    public ProductId() {
        super();
    }

    @Override
    protected int hashOddValue() {
        return 0;
    }

    @Override
    protected int hashPrimeValue() {
        return 0;
    }
    @Override
    protected void validateId(String anId) {

    }
    
}

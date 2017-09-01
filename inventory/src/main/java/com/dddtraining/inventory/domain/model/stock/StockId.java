package com.dddtraining.inventory.domain.model.stock;

import com.dddtraining.inventory.domain.model.common.AbstractId;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverride(name = "id", column = @Column(name = "stock_id"))
public class StockId extends AbstractId{



    public StockId(String anId) {
        super(anId);

    }

    public StockId() {
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

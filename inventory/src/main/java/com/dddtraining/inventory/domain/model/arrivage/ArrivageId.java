package com.dddtraining.inventory.domain.model.arrivage;

import com.dddtraining.inventory.domain.model.common.AbstractId;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverride(name = "id", column = @Column(name = "arrivage_id"))
public class ArrivageId extends AbstractId{

    public ArrivageId(String anId) {
        super(anId);
    }

	public ArrivageId() {
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

}

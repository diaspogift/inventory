package com.dddtraining.inventory.domain.model.common;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class IdentifiedDomainObject extends  AssertionConcern implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  long id;

    protected IdentifiedDomainObject() {
        super();

        this.setId(-1);
    }

    public long id() {
        return this.id;
    }

    private void setId(long anId) {
        this.id = anId;
    }

}

package com.dddtraining.inventory.domain.model.product;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public final class ProductId {

	@Column(name="PRODUCT_ID")
    private String id;

    public ProductId(String anId) {
        this();

        if (anId == null) {
            throw new IllegalArgumentException("Null IDs are invalid!");

        }
        if (anId.isEmpty()) {
            throw new IllegalArgumentException("Empty IDs are invalid!");
        }

        this.setId(anId);
    }

    public ProductId() {
        super();
    }

    public String id() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductId other = (ProductId) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductId{" +
                "id='" + id + '\'' +
                '}';
    }
}

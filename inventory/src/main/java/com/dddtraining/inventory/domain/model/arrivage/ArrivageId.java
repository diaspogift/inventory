package com.dddtraining.inventory.domain.model.arrivage;

public class ArrivageId {

    private String id;

    public ArrivageId(String anId) {
        this.setId(anId);
    }


    private void setId(String anId) {
        this.id = anId;
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
        ArrivageId other = (ArrivageId) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ArrivageId{" +
                "id='" + id + '\'' +
                '}';
    }

    public String id() {

        return this.id;
    }
}

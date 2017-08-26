package com.dddtraining.inventory.domain.model.stock;

public class Quantity {

    private int value;

    public Quantity(int aValue) {
        super();
        this.setValue(aValue);
    }


    //Business logic

    public Quantity() {
        super();
    }

    public Quantity increment(int aValue) {

        if (aValue <= 0) {
            throw new IllegalArgumentException("Negative value was given");
        }
        return new Quantity(this.value() + aValue);

    }

    public Quantity increment(Quantity aQuantity) {

        if (aQuantity == null) {
            throw new IllegalArgumentException("Negative value was given");
        }
        if (aQuantity.value() <= 0) {
            throw new IllegalArgumentException("Negative value was given");
        }
        return new Quantity(this.value() + aQuantity.value());

    }

    public Quantity decrement(int aValue) {

        if (aValue < 0) {
            throw new IllegalArgumentException("Negative value was given");
        }
        if (this.value() - aValue < 0) {
            throw new IllegalArgumentException("Given quantity superior to availlable quantity");
        }

        return new Quantity(this.value() - aValue);


    }

    //Getters and Setters
    private void setValue(int aValue) {
        this.value = aValue;
    }

    public int value() {
        return this.value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
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
        Quantity other = (Quantity) obj;
        if (value != other.value)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "value=" + value +
                '}';
    }
}
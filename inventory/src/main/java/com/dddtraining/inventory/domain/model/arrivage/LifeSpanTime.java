package com.dddtraining.inventory.domain.model.arrivage;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class LifeSpanTime implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public LifeSpanTime(ZonedDateTime startDate) {
        super();
        this.setStartDate(startDate);
        this.setEndDate(null);
    }


    private LifeSpanTime(ZonedDateTime startDate, ZonedDateTime endDate) {
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    public LifeSpanTime() {
        super();
    }

    //Business logic
    public LifeSpanTime changeDateStockCleared(ZonedDateTime endDate) {

        if (endDate == null) {
            throw new IllegalArgumentException("Invalid Date");
        }
        if (endDate.isBefore(this.startDate())) {
            throw new IllegalArgumentException("Invalid Date");
        }


        return new LifeSpanTime(this.startDate(), endDate);
    }

    //Getters and Setters
    private void setStartDate(ZonedDateTime astartDate) {
        this.startDate = astartDate;

    }

    private void setEndDate(ZonedDateTime aDateStockDateStockCleared) {
        this.endDate = aDateStockDateStockCleared;

    }

    public ZonedDateTime startDate() {
        return this.startDate;
    }

    public ZonedDateTime endDate() {
        return this.endDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
        LifeSpanTime other = (LifeSpanTime) obj;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LifeSpanTime{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    //
	public ZonedDateTime getStartDate() {
		return startDate;
	}


	public ZonedDateTime getEndDate() {
		return endDate;
	}
    
 
    
    
}
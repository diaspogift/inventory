package com.dddtraining.inventory.domain.model.arrivage;

import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.StockId;

import java.time.ZonedDateTime;
import java.util.Set;

public interface ArrivageRepository {

    public ArrivageId nextIdentity();
    public void add(Arrivage anArrivage);
    public void remove (Arrivage anArrivage);
    public Arrivage arrivgeOfId(ArrivageId anArrivageId);
    public Set<Arrivage> allArrivagesPriorTo(ZonedDateTime aDate);
    public Set<Arrivage> allArrivagesAfter(ZonedDateTime aDate);
    public Set<Arrivage> allArrivagesBetweenDates(ZonedDateTime aMinDate, ZonedDateTime aMaxDate);
    public Set<Arrivage> allArrivagesOfProductId(ProductId aProductId);
    public Set<Arrivage> allArrivages();
    public Arrivage sellingSotckArrivage(StockId stockId, int initialOrdering);
}

package com.dddtraining.inventory.domain.model.arrivage;

import java.time.ZonedDateTime;
import java.util.Collection;

import com.dddtraining.inventory.domain.model.product.ProductId;

public interface ArrivageRepository {

    public ArrivageId nextIdentity();

    public void add(Arrivage anArrivage);

    public void remove(Arrivage anArrivage);

    public Arrivage arrivgeOfId(ArrivageId anArrivageId);

    public Collection<Arrivage> allArrivagesPriorTo(ZonedDateTime aDate);

    public Collection<Arrivage> allArrivagesAfter(ZonedDateTime aDate);

    public Collection<Arrivage> allArrivagesBetweenDates(ZonedDateTime aMinDate, ZonedDateTime aMaxDate);

    public Collection<Arrivage> allArrivagesOfProductId(ProductId aProductId);

    public Collection<Arrivage> allArrivages();

}

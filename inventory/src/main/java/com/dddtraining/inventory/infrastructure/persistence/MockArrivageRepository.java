package com.dddtraining.inventory.infrastructure.persistence;

import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Quantity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MockArrivageRepository implements ArrivageRepository {


    private Set<Arrivage> arrivages;

    public MockArrivageRepository() {

        this.arrivages = new HashSet<Arrivage>();

        Arrivage arrivage1 =
                new Arrivage(
                        new ProductId("PR12345"),
                        new ArrivageId("ARR12345"),
                        new Quantity(1000),
                        new BigDecimal(500),
                        "Arrivage de la periode des fetes");


        Arrivage arrivage2 =
                new Arrivage(
                        new ProductId("PR12345"),
                        new ArrivageId("ARR12346"),
                        new Quantity(50),
                        new BigDecimal(550),
                        "Arrivage de la periode des fetes");


        Arrivage arrivage3 =
                new Arrivage(
                        new ProductId("PR12346"),
                        new ArrivageId("ARR12347"),
                        new Quantity(25),
                        new BigDecimal(50000),
                        "Arrivage de la rentre scolaire");

        Arrivage arrivage4 =
                new Arrivage(
                        new ProductId("PR12347"),
                        new ArrivageId("ARR12348"),
                        new Quantity(10000),
                        new BigDecimal(350),
                        "Arrivage de la periode des fetes");

        Arrivage arrivage5 =
                new Arrivage(
                        new ProductId("PR12347"),
                        new ArrivageId("ARR12349"),
                        new Quantity(5000),
                        new BigDecimal(360.50),
                        "Arrivage de la periode des fetes");



        this.arrivages.add(arrivage1);
        this.arrivages.add(arrivage2);
        this.arrivages.add(arrivage3);
        this.arrivages.add(arrivage4);
        this.arrivages.add(arrivage5);


    }

    public void add(Arrivage anArrivage) {

        this.arrivages.add(anArrivage);
    }

    public void remove(Arrivage anArrivage) {

        if(this.arrivages.contains(anArrivage)){

            this.arrivages.remove(anArrivage);
        }
    }

    public Arrivage arrivgeOfId(ArrivageId anArrivageId) {

        Arrivage arrivage = null;

        for(Arrivage nextArrivage : this.arrivages){

            if(nextArrivage.arrivageId().equals(anArrivageId)){
                arrivage = nextArrivage;
            }
        }
        return arrivage;
    }

    public Set<Arrivage> allArrivagesPriorTo(ZonedDateTime aDate) {

        Set<Arrivage> arrivages = new HashSet<Arrivage>();

        for(Arrivage nextArrivage : this.arrivages){

            if(nextArrivage.lifeSpanTime().startDate().isBefore(aDate)){
                arrivages.add(nextArrivage);
            }
        }
        return arrivages;
    }

    public Set<Arrivage> allArrivagesAfter(ZonedDateTime aDate) {

        Set<Arrivage> arrivages = new HashSet<Arrivage>();

        for(Arrivage nextArrivage : this.arrivages){

            if(nextArrivage.lifeSpanTime().startDate().isAfter(aDate)){
                arrivages.add(nextArrivage);
            }
        }
        return arrivages;
    }

    public Set<Arrivage> allArrivagesBetweenDates(ZonedDateTime aMinDate, ZonedDateTime aMaxDate) {

        Set<Arrivage> arrivages = new HashSet<Arrivage>();

        for(Arrivage nextArrivage : this.arrivages){

            if(nextArrivage.lifeSpanTime().startDate().isAfter(aMinDate) && nextArrivage.lifeSpanTime().startDate().isBefore(aMaxDate)){
                arrivages.add(nextArrivage);
            }
        }
        return arrivages;
    }

    public Set<Arrivage> allArrivagesOfProductId(ProductId aProductId) {

        Set<Arrivage> arrivages = new HashSet<Arrivage>();

        for(Arrivage nextArrivage : this.arrivages){

            if(nextArrivage.productId().equals(aProductId)){
                arrivages.add(nextArrivage);
            }
        }
        return arrivages;
    }

    public Set<Arrivage> allArrivages() {
        return Collections.unmodifiableSet(this.arrivages);
    }
}

package com.dddtraining.inventory.port.adapter.persistence;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.dddtraining.inventory.domain.model.arrivage.Arrivage;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageId;
import com.dddtraining.inventory.domain.model.arrivage.ArrivageRepository;
import com.dddtraining.inventory.domain.model.product.ProductId;

@Repository
public class JpaArrivageRepository implements ArrivageRepository {



	   @PersistenceContext
	   private EntityManager entityManager;


   


    public void add(Arrivage anArrivage) {

        this.entityManager().persist(anArrivage);
    }

    public void remove(Arrivage anArrivage) {
    	this.entityManager().remove(anArrivage);
    }

    public Arrivage arrivgeOfId(ArrivageId anArrivageId) {

        Arrivage arrivage = 
        		this.entityManager()
        		.createQuery("select arrivage from Arrivage as arrivage where "
        				+ "arrivage.arrivageId = :arrivageId", Arrivage.class)
        		.setParameter("arrivageId", anArrivageId)
        		.getSingleResult();
  
        return arrivage;
    }

    public Collection<Arrivage> allArrivagesPriorTo(ZonedDateTime aDate) {
    	
    	
    	Collection<Arrivage> arrivages =
    			this.entityManager()
    			.createQuery("select arrivage from Arrivage as arrivage where "
    					+ "arrivage.lifeSpanTime.startDate <= :startDate", Arrivage.class)
    			.setParameter("startDate", aDate)
    			.getResultList();
    			
        return arrivages;
    }

    public Collection<Arrivage> allArrivagesAfter(ZonedDateTime aDate) {

    	Collection<Arrivage> arrivages =
    			this.entityManager()
    			.createQuery("select arrivage from Arrivage as arrivage where "
    					+ "arrivage.lifeSpanTime.startDate >= :startDate", Arrivage.class)
    			.setParameter("startDate", aDate)
    			.getResultList();
    			
        return arrivages;
    }

    public Collection<Arrivage> allArrivagesBetweenDates(ZonedDateTime aMinDate, ZonedDateTime aMaxDate) {
    	
    	
    	Collection<Arrivage> arrivages =
    			this.entityManager()
    			.createQuery("select arrivage from Arrivage as arrivage where "
    					+ " arrivage.lifeSpanTime.startDate >= :minStartDate "
    					+ " and "
    					+ " arrivage.lifeSpanTime.startDate <= :maxStartDade", Arrivage.class)
    			.setParameter("minStartDate", aMinDate)
    			.setParameter("maxStartDade", aMaxDate)
    			.getResultList();
    			
        return arrivages;
    }

    public Collection<Arrivage> allArrivagesOfProductId(ProductId aProductId) {

    	
    	
    	Collection<Arrivage> arrivages =
    			this.entityManager()
    			.createQuery("select arrivage from Arrivage as arrivage where "
    					+ "arrivage.productId = :productId", Arrivage.class)
    			.setParameter("productId", aProductId)
    			.getResultList();
    			
        return arrivages;
    }

    public Collection<Arrivage> allArrivages() {
    	

    	Collection<Arrivage> arrivages =
    			this.entityManager()
    			.createQuery("select arrivage from Arrivage as arrivage", Arrivage.class)
    			.getResultList();
    			
        return arrivages;    
    }

    

    public ArrivageId nextIdentity() {
        return new ArrivageId(UUID.randomUUID().toString().toUpperCase());
    }

	public EntityManager entityManager() {
		return this.entityManager;
	}
    
    
    
  
}

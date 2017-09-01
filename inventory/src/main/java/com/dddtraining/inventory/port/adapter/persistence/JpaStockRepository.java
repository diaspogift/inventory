package com.dddtraining.inventory.port.adapter.persistence;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.stock.Stock;
import com.dddtraining.inventory.domain.model.stock.StockId;
import com.dddtraining.inventory.domain.model.stock.StockRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JpaStockRepository implements StockRepository {


	   @PersistenceContext
	   private EntityManager entityManager;

   



    public void add(Stock aStock) {
        this.entityManager().persist(aStock);
    }

    public void remove(Stock aStock) {
        this.entityManager().remove(aStock);
    }


    public Stock stockOfId(StockId aStockId) {

    	System.out.println("\n In stockOfId aStockId = "+aStockId);
    	
    	Stock stock = 
        		this.entityManager()
        		.createQuery("select stock from Stock as stock where stock.stockId = :stockId", Stock.class)
        		.setParameter("stockId", aStockId)
        		.getSingleResult();


        System.out.println("\n In stockOfId stock = "+stock);

        return stock;
    }

    public Stock stockForProductOfId(ProductId aProductId) {

    	
    	Stock stock = 
        		this.entityManager()
        		.createQuery("select stock from Stock as stock where stock.productId = :productId", Stock.class)
        		.setParameter("productId", aProductId)
        		.getSingleResult();
        		
        return stock;
    }


    public Collection<Stock> allAvailableStock() {

        Collection<Stock> result = 
        		this.entityManager()
        		.createQuery("select stock from Stock as stock where stock.availability = TRUE", Stock.class)
        		.getResultList();
       
        return result;
    }

    public Collection<Stock> allStocks() {
        Collection<Stock> result = 
        		this.entityManager()
        		.createQuery("select stock from Stock as stock", Stock.class)
        		.getResultList();
       
        return result;    
   }


    
    public StockId nextIdentity() {
        return new StockId(UUID.randomUUID().toString().toUpperCase());
    }

	public EntityManager entityManager() {
		return this.entityManager;
	}
    
    
    
}

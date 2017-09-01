package com.dddtraining.inventory.port.adapter.persistence;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.dddtraining.inventory.domain.model.product.AvailabilityStatus;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JpaProductRepository implements ProductRepository {

   @PersistenceContext
   private EntityManager entityManager;


    public void add(Product aProduct) {

    	this.entityManager().persist(aProduct);
    }

    public void remove(Product aProduct) {
    	this.entityManager().remove(aProduct);
    }

    public Product productOfId(ProductId aProductId) {



        Product product = 
        		this.entityManager()
        		.createQuery("select product from Product as product where product.productId = :productId", Product.class)
        		.setParameter("productId", aProductId)
        		.getSingleResult();

        System.out.println("Found Product in "+this.getClass().getSimpleName()+" product =  "+product);
 
        return product;
    }

//TO DO 
    public Collection<Product> allProductOfStatus(AvailabilityStatus aStatus) {

        Collection<Product> products = 
        		this.entityManager()
        		.createQuery("select product from Product as product where product.status = :status", Product.class)
        		.setParameter("status", aStatus)
        		.getResultList();

        return products;
    }

    public Collection<Product> allProducts() {
    	
    	Collection<Product> products = 
        		this.entityManager()
        		.createQuery("select product from Product as product", Product.class)
        		.getResultList();
    			
        return products;
    }
    

    public ProductId nextIdentity() {
        return new ProductId(UUID.randomUUID().toString().toUpperCase());
    }

	public EntityManager entityManager() {
		return this.entityManager;
	}



    
    
    
    
    
    
    
    
    
}

package com.dddtraining.inventory.port.adpter.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dddtraining.inventory.domain.model.product.AvailabilityStatus;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;

import javax.persistence.NoResultException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProductRepositoryTest {

	@Autowired
    private ProductRepository productRepository;

    @Test
    public void testAddProduct() {


    	ProductId productId = this.productRepository.nextIdentity();

        Product product =
                new Product(
                		productId,
                        "PRODUCT NAME",
                        "PRODUCT DESCRIPTION");

        productRepository.add(product);


        Product savedProduct = productRepository.productOfId(productId);

        assertEquals(product, savedProduct);

    }


    @Test(expected = EmptyResultDataAccessException.class)
    public void testRemoveProduct() {

    	ProductId productId = this.productRepository.nextIdentity();


    	  Product product =
                  new Product(
                  		productId,
                          "PRODUCT NAME",
                          "PRODUCT DESCRIPTION");

        productRepository.add(product);

        Product saveProduct = this.productRepository.productOfId(productId);

        assertEquals(product, saveProduct);

        productRepository.remove(product);

        saveProduct = this.productRepository.productOfId(productId);


        assertNull(saveProduct);


    }


    @Test
    public void testProductOfId() {

    	ProductId productId = this.productRepository.nextIdentity();

        Product product =
                new Product(
                		productId,
                        "PRODUCT NAME",
                        "PRODUCT DESCRIPTION");

        productRepository.add(product);


        Product foundProduct = productRepository.productOfId(productId);

        assertEquals(product.productId(), foundProduct.productId());

    }

    @Test
    public void TestAllProductOfStatus() {
    	
    	
    	ProductId productId1 = this.productRepository.nextIdentity();
        Product product1 = new Product(productId1,"PRODUCT NAME 1","PRODUCT DESCRIPTION 1");
       
        
    	ProductId productId2 = this.productRepository.nextIdentity();
        Product product2 = new Product(productId2,"PRODUCT NAME 2","PRODUCT DESCRIPTION 2");  
        
    	ProductId productId3 = this.productRepository.nextIdentity();
        Product product3 = new Product(productId3,"PRODUCT NAME 3","PRODUCT DESCRIPTION 3", AvailabilityStatus.STOCK_PROVIDED);
        
        

        this.productRepository.add(product1);
        this.productRepository.add(product2);
        this.productRepository.add(product3);
        
        
        Collection<Product> productsWithCreatedStatus = productRepository.allProductOfStatus(AvailabilityStatus.CREATED);
        assertEquals(2, productsWithCreatedStatus.size());

        for (Product nextProduct : productsWithCreatedStatus) {

            assertEquals(AvailabilityStatus.CREATED, nextProduct.status());
        }

        Collection<Product> productsWithStockProvidedStatus = productRepository.allProductOfStatus(AvailabilityStatus.STOCK_PROVIDED);
        assertEquals(1, productsWithStockProvidedStatus.size());
        
        for (Product nextProduct : productsWithStockProvidedStatus) {

            assertEquals(AvailabilityStatus.STOCK_PROVIDED, nextProduct.status());
        }

     

    }


    @Test
    public void testAllProducts() {
    	
    	ProductId productId1 = this.productRepository.nextIdentity();
        Product product1 = new Product(productId1,"PRODUCT NAME 1","PRODUCT DESCRIPTION 1");
       
        
    	ProductId productId2 = this.productRepository.nextIdentity();
        Product product2 = new Product(productId2,"PRODUCT NAME 2","PRODUCT DESCRIPTION 2");  
        
    	ProductId productId3 = this.productRepository.nextIdentity();
        Product product3 = new Product(productId3,"PRODUCT NAME 3","PRODUCT DESCRIPTION 3", AvailabilityStatus.THRESHOLD_REACHED);
        
        

        this.productRepository.add(product1);
        this.productRepository.add(product2);
        this.productRepository.add(product3);
        
        
        Collection<Product> allProducts = productRepository.allProducts();
        assertEquals(3, allProducts.size());

    }

}

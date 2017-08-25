package com.dddtraining.inventory.port.adpter.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import com.dddtraining.inventory.port.adapter.persistence.MockProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dddtraining.inventory.domain.model.product.AvailabilityStatus;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Test
    public void testAddProduct() {

        ProductRepository productRepository = new MockProductRepository();

        assertEquals(3, productRepository.allProducts().size());


        Product prod4 =
                new Product(
                        new ProductId("PRODUCT_ID_4"),
                        "Ciment de CimentCam",
                        "Le meilleur sur le marche: apres Dangote Ciment!");

        productRepository.add(prod4);

        assertEquals(4, productRepository.allProducts().size());

        Product savedProduct = productRepository.productOfId(new ProductId("PRODUCT_ID_4"));

        assertEquals(prod4, savedProduct);

    }


    @Test
    public void testRemoveProduct() {

        ProductRepository productRepository = new MockProductRepository();

        assertEquals(3, productRepository.allProducts().size());

        Product prod1 = productRepository.productOfId(new ProductId("PRODUCT_ID_1"));

        productRepository.remove(prod1);

        assertEquals(2, productRepository.allProducts().size());

    }


    @Test
    public void testProductOfId() {

        Product ExpectedProd1 =
                new Product(
                        new ProductId("PRODUCT_ID_1"),
                        "Petit martaux",
                        "Tres solide");

        ProductRepository productRepository = new MockProductRepository();

        Product ActualProd1 = productRepository.productOfId(new ProductId("PRODUCT_ID_1"));

        assertEquals(ExpectedProd1, ActualProd1);

    }

    @Test
    public void TestAllProductOfStatus(){

        ProductRepository productRepository = new MockProductRepository();

        Collection<Product> productsWithCreatedStatus = productRepository.allProductOfStatus(AvailabilityStatus.CREATED);
        assertEquals(3, productsWithCreatedStatus.size());

        for(Product nextProduct : productsWithCreatedStatus){

            assertEquals(AvailabilityStatus.CREATED, nextProduct.status());
        }

        Collection<Product> productsWithStockProvidedStatus = productRepository.allProductOfStatus(AvailabilityStatus.STOCK_PROVIDED);
        assertEquals(0, productsWithStockProvidedStatus.size());

        Collection<Product> productsWithThresholdReachedStatus = productRepository.allProductOfStatus(AvailabilityStatus.THRESHOLD_REACHED);
        assertEquals(0, productsWithThresholdReachedStatus.size());

        Collection<Product> productsWithStockClearedStatus = productRepository.allProductOfStatus(AvailabilityStatus.STOCK_CLEARED);
        assertEquals(0, productsWithStockClearedStatus.size());


    }


    @Test
    public void testAllProducts() {

        ProductRepository productRepository = new MockProductRepository();

        Collection<Product> allProducts = productRepository.allProducts();
        assertEquals(3, allProducts.size());

    }

}

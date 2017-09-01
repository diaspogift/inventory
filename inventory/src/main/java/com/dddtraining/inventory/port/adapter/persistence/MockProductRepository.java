package com.dddtraining.inventory.port.adapter.persistence;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.dddtraining.inventory.domain.model.product.AvailabilityStatus;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import com.dddtraining.inventory.domain.model.stock.StockId;

//@Repository
public class MockProductRepository  {

    private Set<Product> allProducts;

    public MockProductRepository() {
        this.allProducts = new HashSet<Product>();

        Product prod1 =
                new Product(
                        new ProductId("PRODUCT_ID_1"),
                        new StockId("STOCK_ID_1"),
                        "Petit martaux",
                        "Tres solide");


        Product prod2 =
                new Product(
                        new ProductId("PRODUCT_ID_2"),
                        "Bidet Francais",
                        "Tres resistant, pas de fuites");

        Product prod3 =
                new Product(
                        new ProductId("PRODUCT_ID_3"),
                        "Ciment dangote",
                        "Le meilleur sur le marche: qualite incroyable!");

        this.allProducts.add(prod1);
        this.allProducts.add(prod2);
        this.allProducts.add(prod3);

    }

    public ProductId nextIdentity() {
        return new ProductId(UUID.randomUUID().toString().toUpperCase());
    }

    public void add(Product aProduct) {

        this.allProducts.add(aProduct);

    }

    public void remove(Product aProduct) {

        if (this.allProducts.contains(aProduct)) {
            this.allProducts.remove(aProduct);
        }

    }

    public Product productOfId(ProductId aProductId) {

        Product product = null;

        for (Product nextProduct : this.allProducts) {
            if (nextProduct.productId().equals(aProductId)) {
                product = nextProduct;
            }
        }
        return product;
    }


    public Set<Product> allProductOfStatus(AvailabilityStatus aStatus) {

        Set<Product> products = new HashSet<Product>();

        for (Product nextProduct : this.allProducts) {
            if (nextProduct.status().equals(aStatus)) {
                products.add(nextProduct);
            }
        }
        return products;
    }

    public Set<Product> allProducts() {
        return this.allProducts;
    }

}

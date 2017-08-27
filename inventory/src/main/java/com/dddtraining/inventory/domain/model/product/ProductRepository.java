package com.dddtraining.inventory.domain.model.product;

import java.util.Collection;

public interface ProductRepository {

    public ProductId nextIdentity();

    public void add(Product aProduct);

    public void remove(Product aProduct);

    public Product productOfId(ProductId aProductId);

    public Collection<Product> allProductOfStatus(AvailabilityStatus aStatus);

    public Collection<Product> allProducts();

}

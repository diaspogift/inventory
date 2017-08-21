package com.dddtraining.inventory.domain.model.product;

import java.util.Set;

public interface ProductRepository {

	public ProductId nextIdentity();
	public void  add(Product aProduct);
	public void remove(Product aProduct);
	public Product productOfId(ProductId aProductId);
	public Set<Product> allProductOfStatus(AvailabilityStatus aStatus);
	public Set<Product> allProducts();

}

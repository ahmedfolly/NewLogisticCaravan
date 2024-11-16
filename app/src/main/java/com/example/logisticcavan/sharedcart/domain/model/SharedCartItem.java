package com.example.logisticcavan.sharedcart.domain.model;

import com.example.logisticcavan.products.getproducts.domain.Product;

public class SharedCartItem {

    private Product product;
    private SharedProduct sharedProduct;

    public SharedCartItem(Product product, SharedProduct sharedProduct) {
        this.product = product;
        this.sharedProduct = sharedProduct;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SharedProduct getSharedProduct() {
        return sharedProduct;
    }

    public void setSharedProduct(SharedProduct sharedProduct) {
        this.sharedProduct = sharedProduct;
    }
}

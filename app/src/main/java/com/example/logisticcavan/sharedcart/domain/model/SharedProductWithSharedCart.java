package com.example.logisticcavan.sharedcart.domain.model;

import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;

public class SharedProductWithSharedCart {
    private List<SharedProduct> sharedProducts ;
    private List<Product> products;

    public List<SharedProduct> getSharedProducts() {
        return sharedProducts;
    }

    public void setSharedProducts(List<SharedProduct> sharedProducts) {
        this.sharedProducts = sharedProducts;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

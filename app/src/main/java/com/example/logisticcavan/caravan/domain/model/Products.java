package com.example.logisticcavan.caravan.domain.model;

import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;

public class Products {

   private List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public Products() {

    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

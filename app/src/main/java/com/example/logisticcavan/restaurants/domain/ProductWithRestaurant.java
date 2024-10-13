package com.example.logisticcavan.restaurants.domain;

import com.example.logisticcavan.products.getproducts.domain.Product;

public class ProductWithRestaurant {
    private Product product;
    private Restaurant restaurant;

    public ProductWithRestaurant(Product product, Restaurant restaurant) {
        this.product = product;
        this.restaurant = restaurant;
    }

    // Getters and setters for product and restaurant
    // ...

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}

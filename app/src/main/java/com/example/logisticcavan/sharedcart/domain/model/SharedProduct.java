package com.example.logisticcavan.sharedcart.domain.model;

import androidx.annotation.NonNull;

public class SharedProduct {
    private String productId=null;
    private int quantity=0;
    private String addedBy=null;
    private String sharedProductId;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getSharedProductId() {
        return sharedProductId;
    }

    public void setSharedProductId(String sharedProductId) {
        this.sharedProductId = sharedProductId;
    }

    @NonNull
    @Override
    public String toString() {
        return "shared product id "+getSharedProductId();
    }
}

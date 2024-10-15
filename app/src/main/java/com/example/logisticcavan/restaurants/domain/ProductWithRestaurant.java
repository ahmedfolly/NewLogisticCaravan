package com.example.logisticcavan.restaurants.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.logisticcavan.products.getproducts.domain.Product;


public class ProductWithRestaurant implements Parcelable {
    private Product product;
    private Restaurant restaurant;

    public ProductWithRestaurant(Product product, Restaurant restaurant) {
        this.product = product;
        this.restaurant = restaurant;
    }

    // Getters and setters for product and restaurant
    // ...

    protected ProductWithRestaurant(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        restaurant = in.readParcelable(Restaurant.class.getClassLoader());
    }

    public static final Creator<ProductWithRestaurant> CREATOR = new Creator<ProductWithRestaurant>() {
        @Override
        public ProductWithRestaurant createFromParcel(Parcel in) {
            return new ProductWithRestaurant(in);
        }

        @Override
        public ProductWithRestaurant[] newArray(int size) {
            return new ProductWithRestaurant[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(product, i);
        parcel.writeParcelable(restaurant, i);
    }
}

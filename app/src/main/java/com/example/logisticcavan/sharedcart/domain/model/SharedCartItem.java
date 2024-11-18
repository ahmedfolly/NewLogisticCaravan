package com.example.logisticcavan.sharedcart.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.logisticcavan.products.getproducts.domain.Product;

public class SharedCartItem implements Parcelable {

    private Product product;
    private SharedProduct sharedProduct;

    public SharedCartItem(Product product, SharedProduct sharedProduct) {
        this.product = product;
        this.sharedProduct = sharedProduct;
    }


    protected SharedCartItem(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        sharedProduct = in.readParcelable(SharedProduct.class.getClassLoader());
    }

    public static final Creator<SharedCartItem> CREATOR = new Creator<SharedCartItem>() {
        @Override
        public SharedCartItem createFromParcel(Parcel in) {
            return new SharedCartItem(in);
        }

        @Override
        public SharedCartItem[] newArray(int size) {
            return new SharedCartItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(product, i);
        parcel.writeParcelable(sharedProduct, i);
    }
}

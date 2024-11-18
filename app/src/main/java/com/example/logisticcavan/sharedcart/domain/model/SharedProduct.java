package com.example.logisticcavan.sharedcart.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SharedProduct implements Parcelable {
    private String productId=null;
    private int quantity=0;
    private String addedBy=null;
    private String sharedProductId;


    public SharedProduct(){

    }
    protected SharedProduct(Parcel in) {
        productId = in.readString();
        quantity = in.readInt();
        addedBy = in.readString();
        sharedProductId = in.readString();
    }

    public static final Creator<SharedProduct> CREATOR = new Creator<SharedProduct>() {
        @Override
        public SharedProduct createFromParcel(Parcel in) {
            return new SharedProduct(in);
        }

        @Override
        public SharedProduct[] newArray(int size) {
            return new SharedProduct[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeInt(quantity);
        parcel.writeString(addedBy);
        parcel.writeString(sharedProductId);
    }
}

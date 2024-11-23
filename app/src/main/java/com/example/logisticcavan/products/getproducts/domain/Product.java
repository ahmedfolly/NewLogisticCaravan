package com.example.logisticcavan.products.getproducts.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class Product implements Parcelable {

    private String productName = "";
    private String productCategory = "";
    private String productImageLink = "";
    private double productPrice = 0.0;
    private String resId = "";

    private String foodDesc = "";
    private String productID = "";

    private Long expirationData = 0L;
    private Long removalDate = 0L;
    private String statusExpiration = "";
    private String productOwner;

    public Product() {
    }

    public String getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(String productOwner) {
        this.productOwner = productOwner;
    }

    protected Product(Parcel in) {
        productName = in.readString();
        productCategory = in.readString();
        productImageLink = in.readString();
        productPrice = in.readDouble();
        resId = in.readString();
        foodDesc = in.readString();
        productID = in.readString();

        //new
        expirationData = in.readLong();
        removalDate = in.readLong();
        statusExpiration = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductImageLink() {
        return productImageLink;
    }

    public void setProductImageLink(String productImageLink) {
        this.productImageLink = productImageLink;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(productName);
        parcel.writeString(productCategory);
        parcel.writeString(productImageLink);
        parcel.writeDouble(productPrice);
        parcel.writeString(resId);
        parcel.writeString(foodDesc);
        parcel.writeString(productID);

        //new
        parcel.writeLong(expirationData);
        parcel.writeLong(removalDate);
        parcel.writeString(statusExpiration);

    }

    public Long getExpirationData() {
        return expirationData;
    }

    public void setExpirationData(Long expirationData) {
        this.expirationData = expirationData;
    }

    public Long getRemovalDate() {
        return removalDate;
    }

    public void setRemovalDate(Long removalDate) {
        this.removalDate = removalDate;
    }

    public String getStatusExpiration() {
        return statusExpiration;
    }

    public void setStatusExpiration(String statusExpiration) {
        this.statusExpiration = statusExpiration;
    }
}

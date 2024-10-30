package com.example.logisticcavan.restaurants.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Restaurant implements Parcelable {
    private String restaurantName="";
    private String restaurantImageLink="";
    private String restaurantId="";
    private String restaurantLogoLink="";
    private String availableTime="";
    private String deliveryTime="";
    int totalReviewers = 0;
    int totalStars = 0;

    public int getTotalReviewers() {
        return totalReviewers;
    }

    public void setTotalReviewers(int totalReviewers) {
        this.totalReviewers = totalReviewers;
    }

    public int getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(int totalStars) {
        this.totalStars = totalStars;
    }

    public String getRestaurantLogoLink() {
        return restaurantLogoLink;
    }

    public void setRestaurantLogoLink(String restaurantLogoLink) {
        this.restaurantLogoLink = restaurantLogoLink;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    protected Restaurant(Parcel in) {
        restaurantName = in.readString();
        restaurantImageLink = in.readString();
        restaurantId = in.readString();
    }

    public Restaurant(){}

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    private void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantImageLink() {
        return restaurantImageLink;
    }

    private void setRestaurantImageLink(String restaurantImageLink) {
        this.restaurantImageLink = restaurantImageLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(restaurantName);
        parcel.writeString(restaurantImageLink);
        parcel.writeString(restaurantId);
    }
}

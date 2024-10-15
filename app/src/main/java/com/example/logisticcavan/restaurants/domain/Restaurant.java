package com.example.logisticcavan.restaurants.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Restaurant implements Parcelable {
    private String restaurantName="";
    private String restaurantImageLink="";

    private String restaurantId="";

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

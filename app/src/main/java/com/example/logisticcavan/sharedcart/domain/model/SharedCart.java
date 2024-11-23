package com.example.logisticcavan.sharedcart.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.List;

public class SharedCart implements Parcelable {
    String adminId = null;
    List<String> userIds = null;
    Timestamp createdAt = null;
    String restaurantId = null;
    String restaurantName=null;
    String sharedCartId = null;

    public SharedCart(){}

    protected SharedCart(Parcel in) {
        adminId = in.readString();
        userIds = in.createStringArrayList();
        createdAt = in.readParcelable(Timestamp.class.getClassLoader());
        restaurantId = in.readString();
        restaurantName = in.readString();
        sharedCartId = in.readString();
    }

    public static final Creator<SharedCart> CREATOR = new Creator<SharedCart>() {
        @Override
        public SharedCart createFromParcel(Parcel in) {
            return new SharedCart(in);
        }

        @Override
        public SharedCart[] newArray(int size) {
            return new SharedCart[size];
        }
    };

    public String getSharedCartId() {
        return sharedCartId;
    }

    public void setSharedCartId(String sharedCartId) {
        this.sharedCartId = sharedCartId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(adminId);
        parcel.writeStringList(userIds);
        parcel.writeParcelable(createdAt, i);
        parcel.writeString(restaurantId);
        parcel.writeString(restaurantName);
        parcel.writeString(sharedCartId);
    }
}

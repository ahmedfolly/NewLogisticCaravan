package com.example.logisticcavan.sharedcart.domain.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class SharedCart {
    String adminId = null;
    List<String> userIds = null;
    Timestamp createdAt = null;

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
}

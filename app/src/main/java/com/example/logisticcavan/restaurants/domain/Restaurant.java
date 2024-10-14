package com.example.logisticcavan.restaurants.domain;

public class Restaurant {
    private String restaurantName="";
    private String restaurantImageLink="";

    private String restaurantId="";

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
}

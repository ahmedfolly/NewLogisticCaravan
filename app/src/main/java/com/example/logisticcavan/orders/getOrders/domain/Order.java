package com.example.logisticcavan.orders.getOrders.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements Parcelable {

    private String dateCreated = "";
    private String from = "";
    private String payment = "";
    private double price = 0.0;
    private String orderId = "";
    private String status = "";
    private String to = "";
    private int totalAmount = 0;
    private double totalCost = 0.0;
    private List<Map<String,Object>> cartItems;
    private String clientName="";
    private String RestaurantName="";

    private String clientLocation ="";

    private String clientPhone="";

    private Map<String,String> courier;
    private Map<String,String> deliveryTime;
    private Map<String,String> location;
    private Map<String,String> customer;
    private Map<String,String> restaurant;
    private Map<String,Object> generalDetails;

    private List<String> customers;

    protected Order(Parcel in) {
        dateCreated = in.readString();
        from = in.readString();
        payment = in.readString();
        price = in.readDouble();
        orderId = in.readString();
        status = in.readString();
        to = in.readString();
        totalAmount = in.readInt();
        totalCost = in.readDouble();
        clientName = in.readString();
        RestaurantName = in.readString();
        clientLocation = in.readString();
        clientPhone = in.readString();

        cartItems = new ArrayList<>();
        in.readList(cartItems, Map.class.getClassLoader());

        courier = new HashMap<>();
        in.readMap(courier, String.class.getClassLoader());

        deliveryTime = new HashMap<>();
        in.readMap(deliveryTime, String.class.getClassLoader());

        location = new HashMap<>();
        in.readMap(location, String.class.getClassLoader());

        customer = new HashMap<>();
        in.readMap(customer, String.class.getClassLoader());

        restaurant = new HashMap<>();
        in.readMap(restaurant, String.class.getClassLoader());

        generalDetails = new HashMap<>();
        in.readMap(generalDetails, Object.class.getClassLoader());

        customers = new ArrayList<>();
        in.readList(customers, Map.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public Map<String, String> getCustomer() {
        return customer;
    }

    public void setCustomer(Map<String, String> customer) {
        this.customer = customer;
    }

    public Map<String, String> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Map<String, String> restaurant) {
        this.restaurant = restaurant;
    }

    public Map<String, Object> getGeneralDetails() {
        return generalDetails;
    }

    public void setGeneralDetails(Map<String, Object> generalDetails) {
        this.generalDetails = generalDetails;
    }

    public Map<String, String> getCourier() {
        return courier;
    }

    public void setCourier(Map<String, String> courier) {
        this.courier = courier;
    }

    public Map<String, String> getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Map<String, String> deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Map<String, String> getLocation() {
        return location;
    }

    public void setLocation(Map<String, String> location) {
        this.location = location;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public List<Map<String, Object>> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Map<String, Object>> cartItems) {
        this.cartItems = cartItems;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public Order() {
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<String> getCustomers() {
        return customers;
    }

    public void setCustomers(List<String> customers) {
        this.customers = customers;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(dateCreated);
        parcel.writeString(from);
        parcel.writeString(payment);
        parcel.writeDouble(price);
        parcel.writeString(orderId);
        parcel.writeString(status);
        parcel.writeString(to);
        parcel.writeInt(totalAmount);
        parcel.writeDouble(totalCost);
        parcel.writeString(clientName);
        parcel.writeString(RestaurantName);
        parcel.writeString(clientLocation);
        parcel.writeString(clientPhone);

        parcel.writeList(cartItems);
        parcel.writeMap(courier);
        parcel.writeMap(deliveryTime);
        parcel.writeMap(location);
        parcel.writeMap(customer);
        parcel.writeMap(restaurant);
        parcel.writeMap(generalDetails);
        parcel.writeList(customers);
    }
}

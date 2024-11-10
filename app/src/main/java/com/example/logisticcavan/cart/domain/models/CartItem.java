package com.example.logisticcavan.cart.domain.models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "cart_table")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String restaurantId;
    private String productName;
    private String productImageLink;
    private double price;
    private int quantity;
    private String productId;
    private String restaurantName;
    private String categoryName;




    public CartItem() {
    }

    public CartItem(int id,
                    String restaurantId,
                    String productName,
                    String productImageLink,
                    double price, int quantity,
                    String productId,
                    String restaurantName,
                    String categoryName) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.productName = productName;
        this.productImageLink = productImageLink;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
        this.restaurantName = restaurantName;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageLink() {
        return productImageLink;
    }

    public void setProductImageLink(String productImageLink) {
        this.productImageLink = productImageLink;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

package com.example.logisticcavan.cart.presentaion;

import android.widget.ImageButton;
import android.widget.TextView;

import com.example.logisticcavan.cart.domain.models.CartItem;

import javax.annotation.Nullable;

public class DetectQuantityUtil {
    private static UpdateCartItemCallback updateCartItemCallback;
    public static void setUpdateCartItemCallback(UpdateCartItemCallback updateCartItemCallback) {
        DetectQuantityUtil.updateCartItemCallback = updateCartItemCallback;
    }

    public static void increaseOrderItemQuantity(String flag, @Nullable CartItem cartItem, ImageButton increaseOrderQuantityBtn, TextView totalPriceTxt, TextView orderQuantity, double price) {
        increaseOrderQuantityBtn.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(orderQuantity.getText().toString());
            currentQuantity += 1;
            orderQuantity.setText(String.valueOf(currentQuantity));
            calculateTotalPrice(totalPriceTxt, currentQuantity, price);
            if (flag.equals("cart") && cartItem != null){
                updateCartItemCallback.updateCartItem(cartItem.getId(),currentQuantity,Double.parseDouble(totalPriceTxt.getText().toString()));
            }
        });
    }

    public static void decreaseOrderItemQuantity(String flag,CartItem cartItem,ImageButton decreaseOrderQuantityBtn, TextView totalPriceTxt, TextView orderQuantity, double price) {
        decreaseOrderQuantityBtn.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(orderQuantity.getText().toString());
            if (currentQuantity > 1) {
                currentQuantity -= 1;
                orderQuantity.setText(String.valueOf(currentQuantity));
                calculateTotalPrice(totalPriceTxt, currentQuantity, price);
                if (flag.equals("cart") && cartItem != null){
                    updateCartItemCallback.updateCartItem(cartItem.getId(),currentQuantity,Double.parseDouble(totalPriceTxt.getText().toString()));
                }
            }
        });
    }
    public static void calculateTotalPrice(TextView totalPriceTxt, int quantity, double price) {
        double totalPrice = quantity * price;
        if (totalPriceTxt!=null){
            totalPriceTxt.setText(String.valueOf(totalPrice));
        }
    }
//    public void updateQuantity(int id, int quantity){
//        updateCartItemCallback.updateCartItem(id,quantity);
//    }

    public interface UpdateCartItemCallback{
        void updateCartItem(int id, int quantity, double price);
    }
}

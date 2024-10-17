package com.example.logisticcavan.navigations.commonui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.logisticcavan.R;
import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.presentaion.CartViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
//        private int id;
//        private String restaurantId;
//        private String productName;
//        private String productImageLink;
//        private double price;
//        private int quantity;
//        private String productId;
        cartViewModel.addToCart(new CartItem(0, "resId",
                "Any name",
                "any link",
                100.0,
                10,
                "any id"));
    }
}
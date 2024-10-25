package com.example.logisticcavan.navigations.commonui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.cart.presentaion.ui.CartFragment;
import com.example.logisticcavan.R;
import com.example.logisticcavan.products.getproducts.presentation.GetProductsViewModel;
import com.example.logisticcavan.restaurants.presentation.GetRestaurantProductsViewModel;
import com.example.logisticcavan.restaurants.presentation.GetRestaurantsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements CartFragment.CartFragmentOpenedCallback {
    private BottomNavigationView bottomNavigationView;
    GetRestaurantsViewModel getRestaurantsViewModel;
    GetRestaurantProductsViewModel viewModel;
    GetProductsViewModel getProductsViewModel;
    CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

    }
    @Override
    public void onCartFragmentOpened() {
        bottomNavigationView.animate().translationY(bottomNavigationView.getHeight()).setDuration(300).withEndAction(() -> {
            bottomNavigationView.setVisibility(View.GONE);
        });
    }

}

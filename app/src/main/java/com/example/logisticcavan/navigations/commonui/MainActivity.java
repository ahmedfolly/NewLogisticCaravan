package com.example.logisticcavan.navigations.commonui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.cart.presentaion.ui.CartFragment;
import com.example.logisticcavan.R;
import com.example.logisticcavan.orders.getOrders.presentaion.GetOrdersOfCurrUserViewModel;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.products.getproducts.presentation.GetProductsViewModel;
import com.example.logisticcavan.products.recommendations.data.GetRecommendationsRepoImp;
import com.example.logisticcavan.products.recommendations.presentation.RecommendationViewModel;
import com.example.logisticcavan.restaurants.presentation.GetRestaurantProductsViewModel;
import com.example.logisticcavan.restaurants.presentation.GetRestaurantsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements CartFragment.CartFragmentOpenedCallback,
        HomeFragment.HomeFramentOpenedCallback {
    private BottomNavigationView bottomNavigationView;
    GetRestaurantsViewModel getRestaurantsViewModel;
    GetRestaurantProductsViewModel viewModel;
    GetProductsViewModel getProductsViewModel;
    CartViewModel cartViewModel;
    GetOrdersOfCurrUserViewModel getOrdersOfCurrUserViewModel;

    RecommendationViewModel recommendationsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        recommendationsViewModel = new ViewModelProvider(this).get(RecommendationViewModel.class);

        recommendationsViewModel.getProducts().observe(this, myResult -> {
            myResult.handle(
                    products -> {
                        for (Product p : products) {
                            Log.d("TAG", "onCreate: "+p.getProductName());
                        }
                    },
                    error -> {
                    },
                    () -> {
                    }
            );
        });
    }

    @Override
    public void onCartFragmentOpened() {
        bottomNavigationView.animate().alpha(0).setDuration(100).withEndAction(() -> {
            bottomNavigationView.setVisibility(View.GONE);
        });
    }

    @Override
    public void onHomeFragmentOpened() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setAlpha(1f);
    }

}

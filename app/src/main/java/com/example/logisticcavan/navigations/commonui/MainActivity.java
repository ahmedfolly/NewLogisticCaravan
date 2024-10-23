package com.example.logisticcavan.navigations.commonui;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.logisticcavan.CartFragment;
import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.products.getproducts.presentation.GetProductsViewModel;
import com.example.logisticcavan.restaurants.domain.GetRestaurantProductsUseCase;
import com.example.logisticcavan.restaurants.domain.Restaurant;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getProductsViewModel = new ViewModelProvider(this).get(GetProductsViewModel.class);

//        viewModel = new ViewModelProvider(this).get(GetRestaurantProductsViewModel.class);
//        viewModel.getRestaurantProducts("7gRzrEYIpARz3Wbep38l");
//        viewModel.getProductsLiveData().observe(this, myResult -> {
//            myResult.handle(
//                    productsIds -> {
//                        if (productsIds != null)
//                            getProductsViewModel.fetchProducts(productsIds);
//                        getProductsViewModel.getProductsLiveData().observe(this, myResult1 -> {
//                            myResult1.handle(
//                                    products -> {
//                                        for (Product product : products) {
//                                            Log.d("TAG", "onCreate: " + product.getProductName());
//                                        }
//                                    },
//                                    error -> {
//                                    },
//                                    () -> {
//                                    }
//                            );
//                        });
//                    },
//                    error -> {
//                    },
//                    () -> {
//                    }
//            );
//        });
    }

    @Override
    public void onCartFragmentOpened() {
        bottomNavigationView.animate().translationY(bottomNavigationView.getHeight()).setDuration(300).withEndAction(() -> {
            bottomNavigationView.setVisibility(View.GONE);
        });
    }
}

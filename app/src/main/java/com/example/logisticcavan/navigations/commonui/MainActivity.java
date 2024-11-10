package com.example.logisticcavan.navigations.commonui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.logisticcavan.R;
import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;
import com.example.logisticcavan.sharedcart.presentation.AddToSharedCartViewModel;
import com.example.logisticcavan.sharedcart.presentation.GetSharedProductsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity{
    private BottomNavigationView bottomNavigationView;
    AddToSharedCartViewModel addToSharedCartViewModel;
    GetSharedProductsViewModel getSharedCartViewModel;

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
        addToSharedCartViewModel = new ViewModelProvider(this).get(AddToSharedCartViewModel.class);
        getSharedCartViewModel = new ViewModelProvider(this).get(GetSharedProductsViewModel.class);

        getSharedCartViewModel.fetchSharedProducts();
        getSharedCartViewModel.getSharedProductsLiveData().observe(this, myResult -> {
            myResult.handle(
                    sharedProducts -> {
                        for (Product product : sharedProducts.getProducts()){
                            Log.d("TAG", "shared cart prodcut: "+product.getProductName());
                        }

                        for (SharedProduct sharedProduct : sharedProducts.getSharedProducts()){
                            Log.d("TAG", "shared cart prodcut: "+sharedProduct.getAddedBy());
                        }
                    },
                    error->{},
                    ()->{}
            );
        });

    }
    public void disappearBottomNav(){
        bottomNavigationView.animate().alpha(0).setDuration(300).withEndAction(() -> {
            bottomNavigationView.setVisibility(View.GONE);
        });
    }
    public void showBottomNav(){
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setAlpha(1f);
    }
}

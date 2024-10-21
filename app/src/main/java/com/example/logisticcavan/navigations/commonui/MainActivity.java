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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements CartFragment.CartFragmentOpenedCallback {
    private BottomNavigationView bottomNavigationView;

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

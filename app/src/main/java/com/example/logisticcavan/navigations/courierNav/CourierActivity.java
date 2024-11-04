package com.example.logisticcavan.navigations.courierNav;


import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.logisticcavan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;
import androidx.navigation.ui.NavigationUI;

@AndroidEntryPoint
public class CourierActivity extends AppCompatActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_courier);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.host_fragment_courier);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        hundelNavigation(bottomNavigationView, navController);

    }

    private static void hundelNavigation(BottomNavigationView bottomNavigationView, NavController navController) {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.courierHomeFragment) {
                navController.popBackStack(R.id.courierHomeFragment, false);
                navController.navigate(R.id.courierHomeFragment);
                return true;
            } else if (itemId == R.id.notificationFragment) {
                navController.popBackStack(R.id.notificationFragment, false);
                navController.navigate(R.id.notificationFragment);
                return true;
            } else if (itemId == R.id.moreFragment) {
                navController.popBackStack(R.id.moreFragment, false);
                navController.navigate(R.id.moreFragment);
                return true;
            }
            return true;
        });
    }


}
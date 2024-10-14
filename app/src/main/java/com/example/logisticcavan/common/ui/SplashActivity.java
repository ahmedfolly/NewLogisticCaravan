package com.example.logisticcavan.common.ui;

import static com.example.logisticcavan.common.utils.Constant.COURIER;
import static com.example.logisticcavan.common.utils.Constant.CUSTOMER;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.presentation.AuthActivity;
import com.example.logisticcavan.users.courier.CourierActivity;
import com.example.logisticcavan.users.customer.MainActivity;
import com.example.logisticcavan.users.restaurant.RestaurantActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

    @Inject
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        new Handler()
                .postDelayed(this::isLogged
                        , 2000L);

    }


    public void isLogged() {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
            Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
            startActivity(intent);

        }else {
            Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
            startActivity(intent);
        }
    }

    private void navigateBasedOnUser(String typeUser) {
        if (typeUser.equals(CUSTOMER)) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }else if (typeUser.equals(COURIER)){
            startActivity(new Intent(SplashActivity.this, CourierActivity.class));
        }else {
            startActivity(new Intent(SplashActivity.this, RestaurantActivity.class));
        }

    }

}
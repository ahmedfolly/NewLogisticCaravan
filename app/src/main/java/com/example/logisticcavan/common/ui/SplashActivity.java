package com.example.logisticcavan.common.ui;

import static com.example.logisticcavan.common.utils.Constant.COURIER;
import static com.example.logisticcavan.common.utils.Constant.CUSTOMER;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.domain.useCase.GetUserInfoLocallyUseCase;
import com.example.logisticcavan.auth.presentation.AuthActivity;
import com.example.logisticcavan.navigations.courierNav.CourierActivity;
import com.example.logisticcavan.navigations.commonui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    GetUserInfoLocallyUseCase getUserInfoLocallyUseCase;

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
            navigateBasedOnUser(getUserInfoLocallyUseCase.getUserInfo().getType());

        }else {
            Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
            startActivity(intent);
        }
    }

    private void navigateBasedOnUser(String typeUser) {
        Log.e("TAG", "navigateBasedOnUser:  "+typeUser );

        if (typeUser.equals(CUSTOMER)) {
            Log.e("TAG", "MainActivity:  " );

            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }else if (typeUser.equals(COURIER)){
            Log.e("TAG", "SplashActivity:  " );

            startActivity(new Intent(SplashActivity.this, CourierActivity.class));
        }
    }

}
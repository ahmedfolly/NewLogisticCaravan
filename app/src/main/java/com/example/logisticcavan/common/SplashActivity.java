package com.example.logisticcavan.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.presentation.AuthActivity;
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
          System.out.println("not  null");
        }else {
            System.out.println("null");
            Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
            startActivity(intent);
        }


    }

}
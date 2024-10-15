package com.example.logisticcavan.users.courier;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.presentation.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;

public class CourierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_courier);
        findViewById(R.id.button).setOnClickListener(view1 -> {
            signOut();
        });

    }


    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }
}
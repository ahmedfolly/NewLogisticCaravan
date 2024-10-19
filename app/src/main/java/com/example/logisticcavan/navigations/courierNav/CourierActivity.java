package com.example.logisticcavan.navigations.courierNav;


import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.logisticcavan.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CourierActivity extends AppCompatActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_courier);

    }

}
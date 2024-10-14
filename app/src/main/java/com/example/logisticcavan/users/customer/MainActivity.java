package com.example.logisticcavan.users.customer;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.logisticcavan.R;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.products.getproducts.presentation.GetProductsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    private GetProductsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//<<<<<<< HEAD
//=======
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        viewModel = new ViewModelProvider(this).get(GetProductsViewModel.class);

        viewModel.fetchProducts();

        viewModel.getProductsLiveData().observe(this, result ->
                result.handle(data -> {
            for (Product product : data) {
                Log.d("TAG", "onSuccess: " + product.getProductName());
            }
        }, error -> {
            Log.d("TAG", "onError: " + error.getMessage());
        }, () -> {
            Log.d("TAG", "onLoad: Loading ");
        }));
//>>>>>>> 9280da99b93c61d886c5567aa8d9bfdce2a4d104
    }
}
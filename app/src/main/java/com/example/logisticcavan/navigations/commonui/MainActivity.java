package com.example.logisticcavan.navigations.commonui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
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
    }
}
package com.example.logisticcavan.getExpiredProducts.presentaion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.databinding.FragmentExpiredProductsBinding;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExpiredProductsFragment extends Fragment implements  ExpiredProductsAdapter.ProductListener{


    @Inject
    ExpiredProductsViewModel viewModel;
    private FragmentExpiredProductsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentExpiredProductsBinding.inflate(inflater, container, false);
        setUpClickListener();
        viewModel.getCaravanProducts();
        observeViewModel();
        return binding.getRoot();
    }

    private void setUpClickListener() {

    }

    private void observeViewModel() {
        viewModel.expiredProducts.observe(getViewLifecycleOwner(), products -> {
            if (products.isEmpty()) {
                binding.noExpiredProducts.setVisibility(View.VISIBLE);
            } else {
                updateUiExpiredProducts(products);
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    private void updateUiExpiredProducts(List<Product> products) {
        ExpiredProductsAdapter adapter = new ExpiredProductsAdapter(products,this);
        binding.containerExpiredProducts.setVisibility(View.VISIBLE);
        binding.recyclerView.setAdapter(adapter);
    }


    @Override
    public void onProductClick(Product product) {
        Log.e("TAG","Product Clicked");
    }
}
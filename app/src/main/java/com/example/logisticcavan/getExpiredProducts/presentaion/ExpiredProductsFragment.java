package com.example.logisticcavan.getExpiredProducts.presentaion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.logisticcavan.R;
import com.example.logisticcavan.databinding.FragmentExpiredProductsBinding;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExpiredProductsFragment extends Fragment implements  ExpiredProductsAdapter.ProductListener{


    @Inject
    ExpiredProductsViewModel viewModel;

    private ExpireProductsSoonAdapter expireProductsSoonAdapter;
    private ExpiredProductsAdapter  expiredProductsAdapter;
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
                binding.noExpiredProducts.setVisibility(View.GONE);
                updateUiExpiredProducts(products);
            }
            binding.progressBar.setVisibility(View.GONE);
        }); 
        
        viewModel.expireSoon.observe(getViewLifecycleOwner(), products -> {
            if (products.isEmpty()) {
                binding.noAlert.setVisibility(View.VISIBLE);
            } else {
                binding.noAlert.setVisibility(View.GONE);

                updateUiExpireSoon(products);
            }
            binding.progressAlert.setVisibility(View.GONE);
        });
    }

    private void updateUiExpiredProducts (List<Product> products) {
        expiredProductsAdapter = new ExpiredProductsAdapter(products,this,requireActivity());
        binding.recyclerView.setAdapter(expiredProductsAdapter);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }


    private void updateUiExpireSoon(List<Product> products) {
        expireProductsSoonAdapter = new ExpireProductsSoonAdapter(products);
        binding.recyclerView1.setAdapter(expireProductsSoonAdapter);
        binding.recyclerView1.setVisibility(View.VISIBLE);

    }


    public void showCustomDialog(String productID , long timeStamp) {

        Dialog dialog = new Dialog(requireActivity());
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        dialog.setContentView(dialogView);
        TextView textYes = dialogView.findViewById(R.id.yes);
        TextView textNo = dialogView.findViewById(R.id.textNo);

        textYes.setOnClickListener(v -> {
           viewModel.updateProductStatus(productID,timeStamp);
            dialog.dismiss();

        });
        textNo.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }




    @Override
    public void onProductClick(Product product) {
        long timeStamp = System.currentTimeMillis();
        showCustomDialog(product.getProductID(),timeStamp);
    }
}
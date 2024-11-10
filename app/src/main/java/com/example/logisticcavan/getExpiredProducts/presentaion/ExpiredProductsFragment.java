package com.example.logisticcavan.getExpiredProducts.presentaion;

import static com.example.logisticcavan.common.utils.Constant.GROCERY;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.R;
import com.example.logisticcavan.caravan.presentation.CaravanViewModel;
import com.example.logisticcavan.cart.presentaion.CartViewModel;
import com.example.logisticcavan.databinding.FragmentCaravanBinding;
import com.example.logisticcavan.databinding.FragmentExpiredProductsBinding;
import com.example.logisticcavan.products.getproducts.presentation.RestaurantProductsAdapter;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExpiredProductsFragment extends Fragment {


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
        viewModel.products.observe(getViewLifecycleOwner(), products -> {
            if (products != null) {

            } else {

            }
        });
    }



}
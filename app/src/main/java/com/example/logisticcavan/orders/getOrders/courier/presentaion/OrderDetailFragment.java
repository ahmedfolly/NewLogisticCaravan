package com.example.logisticcavan.orders.getOrders.courier.presentaion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentOrderDetailBinding;
import com.example.logisticcavan.orders.getOrders.domain.Order;


public class OrderDetailFragment extends BaseFragment {

    private FragmentOrderDetailBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrderDetailBinding.inflate(inflater, container, false);
        Order order =   OrderDetailFragmentArgs.fromBundle(requireArguments()).getOrder();
        setUpUiData(order);
        return binding.getRoot();
    }

    private void setUpUiData(Order order) {

    }
}
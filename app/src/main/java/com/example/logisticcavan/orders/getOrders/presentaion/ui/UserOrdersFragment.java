package com.example.logisticcavan.orders.getOrders.presentaion.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.R;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.example.logisticcavan.orders.getOrders.presentaion.GetOrdersOfCurrUserViewModel;
import com.example.logisticcavan.orders.getOrders.presentaion.UserOrdersAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserOrdersFragment extends Fragment {

    private GetOrdersOfCurrUserViewModel ordersOfCurrUserViewModel;
    private UserOrdersAdapter userOrdersAdapter;
    private RecyclerView userOrdersContainer;

    public UserOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ordersOfCurrUserViewModel = new ViewModelProvider(this).get(GetOrdersOfCurrUserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userOrdersContainer = view.findViewById(R.id.user_orders_container);
        getUserOrders();
    }
    private void getUserOrders(){
        ordersOfCurrUserViewModel.getOrdersLiveData().observe(getViewLifecycleOwner(),ordersResult->{
            ordersResult.handle(ordersList->{
                Log.d("TAG", "getUserOrders: "+ordersList.size());

                userOrdersAdapter = new UserOrdersAdapter(ordersList);
                setUserOrdersContainer();
            }, error->{}, ()->{});
        });
    }
    private void setUserOrdersContainer(){
        userOrdersContainer.setHasFixedSize(true);
        userOrdersContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        userOrdersContainer.setAdapter(userOrdersAdapter);
    }
}
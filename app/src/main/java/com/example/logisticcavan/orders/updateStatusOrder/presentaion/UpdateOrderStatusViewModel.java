package com.example.logisticcavan.orders.updateStatusOrder.presentaion;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.orders.updateStatusOrder.domain.UpdateOrderStatusUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UpdateOrderStatusViewModel extends ViewModel {


    UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @Inject
    public UpdateOrderStatusViewModel(UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }


    public void updateOrderStatus(String orderId,String newStatus){
        updateOrderStatusUseCase.execute(orderId,newStatus);
    }
}

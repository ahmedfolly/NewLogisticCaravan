package com.example.logisticcavan.orders.updateStatusOrder.presentaion;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.auth.domain.useCase.GetUserInfoRemotelyUseCase;
import com.example.logisticcavan.orders.updateStatusOrder.domain.UpdateOrderStatusUseCase;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UpdateOrderStatusViewModel extends ViewModel {

     GetUserInfoRemotelyUseCase getUserInfoRemotelyUseCase;

    UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @Inject
    public UpdateOrderStatusViewModel(UpdateOrderStatusUseCase updateOrderStatusUseCase , GetUserInfoRemotelyUseCase getUserInfoRemotelyUseCase) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.getUserInfoRemotelyUseCase = getUserInfoRemotelyUseCase;
    }

    public CompletableFuture<UserInfo> getUserRemotely(String email){
        return getUserInfoRemotelyUseCase.getUserInfo(email);
    }
    public void updateOrderStatus(String orderId,String newStatus){
        updateOrderStatusUseCase.execute(orderId,newStatus);
    }
}

package com.example.logisticcavan.orders.getOrders.courier.presentaion;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.logisticcavan.common.base.BaseViewModel;
import com.example.logisticcavan.orders.getOrders.domain.GetAllOrderUseCaseCase;
import com.example.logisticcavan.orders.getOrders.domain.GetCourierOrdersBasedStatusUseCase;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GetCourierOrdersViewModel extends BaseViewModel {

    private GetCourierOrdersBasedStatusUseCase getCourierOrdersBasedStatusUseCase;
    private GetAllOrderUseCaseCase getAllOrderUseCaseCase;

    private MutableLiveData<List<Order>> _listOrders = new MutableLiveData<>();
    public LiveData<List<Order>> listOrders = _listOrders;


    @Inject
    public GetCourierOrdersViewModel(GetCourierOrdersBasedStatusUseCase getCourierOrdersBasedStatusUseCase, GetAllOrderUseCaseCase getAllOrderUseCaseCase) {
        this.getCourierOrdersBasedStatusUseCase = getCourierOrdersBasedStatusUseCase;
        this.getAllOrderUseCaseCase = getAllOrderUseCaseCase;
    }


    public void getOrdersBasedStatus(String status){
     resultGetOrders( getCourierOrdersBasedStatusUseCase.getOrdersBaseStatus(status));
    }

    public void getAllOrders(){
        resultGetOrders( getAllOrderUseCaseCase.execute());
    }


    private void resultGetOrders(CompletableFuture<List<Order>> result) {
        result.thenAccept(orders -> {
          Log.e("TAG"," ------------->  "+orders.size());
          _listOrders.setValue(orders);
      }).exceptionally(ex -> {
          Log.e("TAG"," ------------->  "+ex.toString());
          setShowErrorMessage(ex.getMessage());
          return null;
      });

    }


}

package com.example.logisticcavan.orders.getOrders.domain;

import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;

public class GetOrdersOfCurrUserUseCase {
    private final GetOrdersOfCurrUser repo;

    public GetOrdersOfCurrUserUseCase(GetOrdersOfCurrUser repo) {
        this.repo = repo;
    }

    public Observable<MyResult<List<Order>>> ordersOfCurrUser(List<String> orderIds) {
        return repo.ordersOfCurrUser(orderIds);
    }
}

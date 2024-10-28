package com.example.logisticcavan.orders.getOrders.domain;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetOrdersIdsUseCase {
    private final GetOrdersOfCurrUser repo;

    public GetOrdersIdsUseCase(GetOrdersOfCurrUser repo) {
        this.repo = repo;
    }
    public Observable<List<String>> getOrdersIds(){
        return repo.getOrdersIds();
    }
}

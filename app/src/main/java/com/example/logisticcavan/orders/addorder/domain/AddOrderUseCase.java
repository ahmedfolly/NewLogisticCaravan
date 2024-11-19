package com.example.logisticcavan.orders.addorder.domain;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class AddOrderUseCase {
    AddOrderRepo repo;
    private Order order;

    public AddOrderUseCase(AddOrderRepo repo) {
        this.repo = repo;
    }

    public Observable<MyResult<String>> execute(Order order) {
        return repo.addOrder(order);
    }
}

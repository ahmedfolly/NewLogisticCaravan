package com.example.logisticcavan.orders.addorder.domain;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import io.reactivex.rxjava3.core.Observable;

public interface AddOrderRepo {
    Observable<MyResult<String>> addOrder(Order order);
}

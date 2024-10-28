package com.example.logisticcavan.orders.getOrders.domain;

import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;

public interface GetOrdersOfCurrUser {
    Observable<MyResult<List<Order>>> ordersOfCurrUser(List<String> orderIds);
    Observable<List<String>> getOrdersIds();
}

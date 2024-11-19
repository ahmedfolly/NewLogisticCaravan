package com.example.logisticcavan.orders.addorder.domain;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface AddOrderRepo {
    Observable<MyResult<String>> addOrder(Order order);
    Single<String> addOrderIdToUser(String orderId,List<String> orderIds);
}

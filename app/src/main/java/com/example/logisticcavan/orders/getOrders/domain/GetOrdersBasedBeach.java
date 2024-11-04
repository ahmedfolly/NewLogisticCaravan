package com.example.logisticcavan.orders.getOrders.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class GetOrdersBasedBeach {

    private  OrderRepository orderRepository;

    @Inject
    public GetOrdersBasedBeach(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public CompletableFuture<List<Order>> execute(String beach){
        return orderRepository.getOrdersBasedBeach(beach);
    }
}


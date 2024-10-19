package com.example.logisticcavan.orders.getOrders.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class GetAllOrderUseCaseCase {

    private  OrderRepository orderRepository;

    @Inject
    public GetAllOrderUseCaseCase(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public CompletableFuture<List<Order>> execute(){
        return orderRepository.getAllOrders();
    }
}

package com.example.logisticcavan.orders.getOrders.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class GetCourierOrdersBasedStatusUseCase {

    private final OrderRepository orderRepository;

    @Inject
    public GetCourierOrdersBasedStatusUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CompletableFuture<List<Order>>  getOrdersBaseStatus(String status){
        return  orderRepository.getCourierOrdersBasedStatus(status);
    }

}

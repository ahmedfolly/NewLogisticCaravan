package com.example.logisticcavan.orders.getOrders.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderRepository {

     CompletableFuture<List<Order>> getCourierOrdersBasedStatus(String status);
     CompletableFuture<List<Order>> getAllOrders();
     CompletableFuture<List<Order>> getOrdersBasedBeach(String beach);
}


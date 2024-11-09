package com.example.logisticcavan.orders.updateStatusOrder.domain;


import com.example.logisticcavan.orders.updateStatusOrder.data.UpdateOrderStatusRepo;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class UpdateOrderStatusUseCase {


    private UpdateOrderStatusRepo updateOrderStatusRepo;

    @Inject
    public UpdateOrderStatusUseCase(UpdateOrderStatusRepo updateOrderStatusRepo) {

        this.updateOrderStatusRepo = updateOrderStatusRepo;
    }

    public CompletableFuture<Void> execute(String orderId, String newStatus) {
        return updateOrderStatusRepo.updateOrderStatus(orderId, newStatus);
    }
}

package com.example.logisticcavan.orders.updateStatusOrder.data;


import static com.example.logisticcavan.common.utils.Constant.STATUS;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class UpdateOrderStatusRepo {

    private FirebaseFirestore firebaseFirestore;

    @Inject
    public UpdateOrderStatusRepo(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }


    public CompletableFuture<Void> updateOrderStatus(String orderId, String newStatus) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        firebaseFirestore
                .collection("Orders")
                .document(orderId)
                .update("generalDetails.status", newStatus)
                .addOnSuccessListener(aVoid -> {
                    Log.e("Firestore", "Order status updated successfully.");
                    future.complete(null);
                })

                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Failed to update order status", e);
                    future.completeExceptionally(e);
                });

        return future;

    }
    }

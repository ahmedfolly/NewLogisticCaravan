package com.example.logisticcavan.orders.getOrders.data;

import static com.example.logisticcavan.common.utils.Constant.BEACH;
import static com.example.logisticcavan.common.utils.Constant.ORDERS;
import static com.example.logisticcavan.common.utils.Constant.STATUS;

import android.util.Log;

import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.example.logisticcavan.orders.getOrders.domain.OrderRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class CourierOrderRepositoryImpl implements OrderRepository {


    private final FirebaseFirestore firebaseFirestore;

    @Inject
    public CourierOrderRepositoryImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }


    @Override
    public CompletableFuture<List<Order>> getCourierOrdersBasedStatus(String status) {

        CompletableFuture<List<Order>> future = new CompletableFuture<>();

        firebaseFirestore.collection(ORDERS).whereEqualTo(STATUS, status).addSnapshotListener((value, error) -> {
            if (error != null) {
                future.completeExceptionally(error);
            } else {
                if (value != null) {
                    future.complete(value.toObjects(Order.class));
                }
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<List<Order>> getAllOrders() {
        CompletableFuture<List<Order>> future = new CompletableFuture<>();
         firebaseFirestore.collection(ORDERS).addSnapshotListener(((value, error) -> {
             if(error != null){
                 Log.e("error", error.getMessage());
                 future.completeExceptionally(error);
             }else {
                 if (value != null) {
                     Log.e("value", value.toString());
                     List<Order> list = value.toObjects(Order.class);
                   future.complete(list);
                 }
             }
         }));
        return future;
    }

    @Override
    public CompletableFuture<List<Order>> getOrdersBasedBeach(String beach) {
        CompletableFuture<List<Order>> future = new CompletableFuture<>();

        firebaseFirestore.collection(ORDERS).whereEqualTo(BEACH, beach).addSnapshotListener((value, error) -> {
            if (error != null) {
                future.completeExceptionally(error);
            } else {
                if (value != null) {
                    future.complete(value.toObjects(Order.class));
                }
            }
        });

        return future;
    }

}

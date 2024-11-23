package com.example.logisticcavan.orders.getOrders.data;

import android.util.Log;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.orders.getOrders.domain.GetOrdersOfCurrUser;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;

public class GetOrdersOfCurrUserRepoImp implements GetOrdersOfCurrUser {
    private final FirebaseFirestore firestore;
    private final FirebaseAuth mAuth;

    public GetOrdersOfCurrUserRepoImp(FirebaseFirestore firestore, FirebaseAuth mAuth) {
        this.firestore = firestore;
        this.mAuth = mAuth;
    }
    @Override
    public Observable<MyResult<List<Order>>> ordersOfCurrUser(List<String> orderIds) {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            if (orderIds.isEmpty()) {
                emitter.onNext(MyResult.success(Collections.emptyList())); // Emit empty result if no IDs
                emitter.onComplete();
                return;
            }
            firestore.collection("Orders")
                    .whereIn(FieldPath.documentId(), orderIds)
                    .addSnapshotListener((v, e) -> {
                        if (e != null) {
                            emitter.onNext(MyResult.error(e));
                            emitter.onComplete();
                        }
                        Log.d("GetOrders", "SnapshotListener triggered");
                        assert v != null;
                        List<Order> orders = v.toObjects(Order.class);
                        emitter.onNext(MyResult.success(orders));
                    });

        });
    }
    @Override
    public Observable<List<String>> getOrdersIds(){
        List<String> orderIds = new ArrayList<>();
        return Observable.create(emitter->{
            firestore.collection("users")
                    .document(getUserEmail())
                    .collection("Orders")
                    .addSnapshotListener((v,e)->{
                        if (e != null) {
                            Log.d("TAG", "getOrdersIds: " + e.getMessage());
                            emitter.onNext(Collections.emptyList()); // Emit empty list on error
                            emitter.onComplete();
                            return;
                        }
                        assert v != null;
                        Log.d("GetOrders", "SnapshotListener triggered");
                        for (DocumentSnapshot documentSnapshot : v.getDocuments()){
                            String orderId = documentSnapshot.getString("orderId");
                            orderIds.add(orderId);
                        }
                        emitter.onNext(orderIds);
                        emitter.onComplete();
                    });
        });

    }
    private String getUserEmail(){
        return Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
    }
}

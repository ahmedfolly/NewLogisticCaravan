package com.example.logisticcavan.caravan.data;

import static com.example.logisticcavan.common.utils.Constant.CARAVAN;
import static com.example.logisticcavan.common.utils.Constant.PRODUCTS;

import android.util.Log;

import com.example.logisticcavan.caravan.domain.CaravanRepository;
import com.example.logisticcavan.caravan.domain.model.Products;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class CaravanRepoImp implements CaravanRepository {

    private FirebaseFirestore firestore;

    @Inject
    public CaravanRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public CompletableFuture<List<Product>> getCaravanProducts() {
        CompletableFuture<List<Product>> future = new CompletableFuture<>();
        firestore.collection(CARAVAN)
                .document(PRODUCTS).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        future.complete(task.getResult().toObject(Products.class).getProducts());
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });
        return future;
    }
}

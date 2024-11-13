package com.example.logisticcavan.updateStatusCaravanProduct.data;

import static com.example.logisticcavan.common.utils.Constant.CARAVAN;
import static com.example.logisticcavan.common.utils.Constant.PRODUCTS;

import android.util.Log;

import com.example.logisticcavan.products.getproducts.domain.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class UpdateStatusCaravanRepo {

    private FirebaseFirestore firestore;

    @Inject
    public UpdateStatusCaravanRepo(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }



    public CompletableFuture<Void> updateStatusCaravanProduct(String productID, long timeStamp) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("caravan")
                .whereArrayContains("productID", productID)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.e("TAG", "addOnSuccessListener");

                    if (!queryDocumentSnapshots.isEmpty()) {
                        Log.e("TAG", "queryDocumentSnapshots not ");
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                            Log.e("TAG", "Updating document for productID: " + productID);
                            document.getReference()
                                    .update( "removalDate", timeStamp) // Update fields
                                    .addOnSuccessListener(aVoid -> {
                                        Log.e("TAG", "Update successful");
                                        future.complete(null);
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("TAG", "Update failed");
                                        future.completeExceptionally(e);
                                    });
                        }
                    } else {
                        // No matching documents found
                        Log.e("TAG", "No documents found with productID:gfdg ->  " + productID);
                        future.completeExceptionally(new Exception("No matching documents found"));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("TAG", "Query failed");
                    future.completeExceptionally(e);
                });

        return future;
    }
}

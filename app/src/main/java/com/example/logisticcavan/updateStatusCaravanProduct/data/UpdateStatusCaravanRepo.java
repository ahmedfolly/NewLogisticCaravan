package com.example.logisticcavan.updateStatusCaravanProduct.data;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.Map;
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

        firestore.collection("caravan")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    boolean found = false;
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        List<Map<String, Object>> products = (List<Map<String, Object>>) document.get("products");
                        if (products != null) {
                            for (Map<String, Object> product : products) {
                                if (productID.equals(product.get("productID"))) {
                                    product.put("removalDate", timeStamp);

                                    document.getReference()
                                            .update("products", products)
                                            .addOnSuccessListener(aVoid -> {
                                                future.complete(null);
                                            })
                                            .addOnFailureListener(future::completeExceptionally);

                                    found = true;
                                    break;
                                }
                            }
                        }
                        if (found) break;
                    }

                    if (!found) {
                        future.completeExceptionally(new Exception("No matching product found"));
                    }
                })
                .addOnFailureListener(future::completeExceptionally);

        return future;
    }
}

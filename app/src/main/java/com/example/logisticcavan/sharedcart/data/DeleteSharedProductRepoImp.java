package com.example.logisticcavan.sharedcart.data;

import android.util.Log;

import com.example.logisticcavan.sharedcart.domain.repo.DeleteSharedProductRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class DeleteSharedProductRepoImp implements DeleteSharedProductRepo {
    private final FirebaseFirestore firestore;
    private final FirebaseAuth firebaseAuth;

    public DeleteSharedProductRepoImp(FirebaseFirestore firestore, FirebaseAuth firebaseAuth) {
        this.firestore = firestore;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Single<String> deleteSharedProduct(String sharedProductId) {
        return Single.create(
                emitter -> {
                    firestore.collection("SharedCart")
                            .whereArrayContains("userIds", userEmail())
                            .get()
                            .addOnSuccessListener(v -> {
                                String sharedCartId = v.getDocuments().get(0).getId();
                                firestore.collection("SharedCart")
                                        .document(sharedCartId)
                                        .collection("SharedProducts")
                                        .document(sharedProductId)
                                        .delete()
                                        .addOnSuccessListener(task -> {
                                            emitter.onSuccess("Product deleted");
                                        }).addOnFailureListener(e->{
                                            emitter.onSuccess("Failed to delete product");
                                        });
                            });
                }
        );
    }

    private String userEmail() {
        return Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
    }
}

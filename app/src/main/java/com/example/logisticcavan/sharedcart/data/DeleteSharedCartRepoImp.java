package com.example.logisticcavan.sharedcart.data;

import android.util.Log;

import com.example.logisticcavan.sharedcart.domain.repo.DeleteSharedCartRepo;
import com.example.logisticcavan.sharedcart.domain.repo.DeleteSharedProductRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class DeleteSharedCartRepoImp implements DeleteSharedCartRepo {
    private final FirebaseFirestore firestore;
    public DeleteSharedCartRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }
    @Override
    public Single<Boolean> deleteSharedCart(String sharedCartId) {
        return Single.create(
                emitter -> {
                    firestore.collection("SharedCart")
                            .document(sharedCartId)
                            .delete()
                            .addOnSuccessListener(v -> {
                                emitter.onSuccess(true);
                            })
                            .addOnFailureListener(e->{
                                Log.d("TAG", "deleteSharedCart: "+e.getMessage());
                                emitter.onSuccess(false);
                            });
                }
        );
    }
}

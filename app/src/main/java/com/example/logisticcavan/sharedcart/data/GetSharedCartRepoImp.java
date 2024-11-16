package com.example.logisticcavan.sharedcart.data;

import android.util.Log;

import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.repo.GetSharedCartRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import io.reactivex.rxjava3.core.Single;

public class GetSharedCartRepoImp implements GetSharedCartRepo {
    private final FirebaseFirestore firestore;
    private final FirebaseAuth auth;
    public GetSharedCartRepoImp(FirebaseFirestore firestore,
                                FirebaseAuth auth) {
        this.firestore = firestore;
        this.auth = auth;
    }
    @Override
    public Single<SharedCart> getSharedCartObject() {
        return Single.create(
                emitter->{
                    firestore.collection("SharedCart")
                            .whereArrayContains("userIds", getUserEmail())
                            .get()
                            .addOnSuccessListener(v->{
                               if (!v.isEmpty()){
                                   SharedCart sharedCart = v.toObjects(SharedCart.class).get(0);
                                   Log.d("TAG", "getAdminId: "+sharedCart.getAdminId());
                                   emitter.onSuccess(sharedCart);
                               }else {
                                   emitter.onError(new Exception("No shared cart found"));
                               }
                            });

                }
        );
    }
    private String getUserEmail(){
        return Objects.requireNonNull(auth.getCurrentUser()).getEmail();
    }
}

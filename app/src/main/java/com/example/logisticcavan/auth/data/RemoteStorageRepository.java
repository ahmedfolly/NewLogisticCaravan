package com.example.logisticcavan.auth.data;

import static com.example.logisticcavan.common.utils.Constant.USERS;

import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class RemoteStorageRepository{

    private final FirebaseFirestore firebaseFirestore;

    @Inject
    public RemoteStorageRepository(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    public CompletableFuture<Void> saveUserInfoToFirebase(UserInfo userInfo) {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        firebaseFirestore.collection(USERS)
                .document(userInfo.getEmail())
                .set(userInfo)
                .addOnSuccessListener(aVoid -> completableFuture.complete(null))
                .addOnFailureListener(e -> completableFuture.completeExceptionally(e));
        return completableFuture;
    }


    public CompletableFuture<UserInfo> getUserInfoFromFirebase(String email) {
        CompletableFuture<UserInfo> completableFuture = new CompletableFuture<>();
        firebaseFirestore.collection(USERS)
                .document(email)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);
                        completableFuture.complete(userInfo);
                    } else {
                        completableFuture.completeExceptionally(new Exception("User not found"));
                    }
                })
                .addOnFailureListener(completableFuture::completeExceptionally);

        return completableFuture;
    }
}

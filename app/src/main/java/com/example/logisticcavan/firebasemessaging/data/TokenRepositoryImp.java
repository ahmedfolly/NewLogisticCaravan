package com.example.logisticcavan.firebasemessaging.data;

import android.util.Log;

import com.example.logisticcavan.firebasemessaging.domain.TokenRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class TokenRepositoryImp implements TokenRepository {

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Inject
    public TokenRepositoryImp(FirebaseFirestore firestore, FirebaseAuth auth) {
        this.firestore = firestore;
        this.auth = auth;
    }

    @Override
    public void storeToken(String token) {
        if (auth.getCurrentUser() != null) {
            String uid = auth.getCurrentUser().getUid();
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            firestore.collection("tokens").document(uid)
                    .set(data)
                    .addOnSuccessListener(aVoid -> Log.d("TAG", "Token successfully stored"))
                    .addOnFailureListener(e -> Log.e("TAG", "Error storing token", e));
        } else {
            Log.e("TAG", "Failed to store token: No authenticated user");
        }
    }
}

package com.example.logisticcavan.rating.addandgetrating.data;

import android.util.Log;

import com.example.logisticcavan.rating.addandgetrating.domain.repo.UpdateTotalRateVarsRepo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class UpdateRateVarsRepoImp implements UpdateTotalRateVarsRepo {
    private final FirebaseFirestore firebaseFirestore;

    public UpdateRateVarsRepoImp(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public void updateRateVars(String restaurantId, int totalStars) {
        //document ref
        DocumentReference documentRef = firebaseFirestore.collection("Sellers").document(restaurantId);
        firebaseFirestore.runTransaction(transaction -> {
                    DocumentSnapshot snapshot = transaction.get(documentRef);
                    //get the current values
                    int currentStars = 0;
                    int currentReviewers = 0;

                    // Check if the document exists
                    if (snapshot.exists()) {
                        // If it exists, retrieve the current values
                        currentStars = snapshot.getLong("totalStars") != null ? snapshot.getLong("totalStars").intValue() : 0;
                        currentReviewers = snapshot.getLong("totalReviewers") != null ? snapshot.getLong("totalReviewers").intValue() : 0;
                    }
                    //update the values
                    int newStars = currentStars + totalStars;
                    int newReviewers = currentReviewers + 1;
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("totalStars", newStars);
                    updates.put("totalReviewers", newReviewers);
                    //upload changes
                    transaction.set(documentRef, updates, SetOptions.merge());
                    return null;
                }).addOnSuccessListener(v -> {
                    Log.d("TAG", "updateRateVars: success");
                })
                .addOnFailureListener(e -> {
                    Log.d("TAG", "updateRateVars: success");
                });
    }
}

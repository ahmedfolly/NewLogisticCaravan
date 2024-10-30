package com.example.logisticcavan.rating.addandgetrating.data;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.rating.addandgetrating.domain.model.Rating;
import com.example.logisticcavan.rating.addandgetrating.domain.repo.AddRateRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AddRateRepoImp implements AddRateRepo {
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth mAuth;

    public AddRateRepoImp(FirebaseFirestore firebaseFirestore, FirebaseAuth mAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.mAuth = mAuth;
    }

    @Override
    public BehaviorSubject<MyResult<Boolean>> addRate(String restaurantId,Rating rating) {
        BehaviorSubject<MyResult<Boolean>> subject = BehaviorSubject.create();
        subject.onNext(MyResult.loading());
        firebaseFirestore.collection("Sellers")
                .document(restaurantId)
                .collection("Ratings")
                .add(rating)
                .addOnSuccessListener(v -> {
                    subject.onNext(MyResult.success(true));
                })
                .addOnFailureListener(e -> {
                    subject.onNext(MyResult.error(e));
                });
        return subject;
    }

    private String getUserId() {
        return Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    }
}

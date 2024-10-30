package com.example.logisticcavan.rating.addandgetrating.data;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.rating.addandgetrating.domain.model.Rating;
import com.example.logisticcavan.rating.addandgetrating.domain.repo.GetRestaurantRatingRepo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetRestaurantRatingsRepoImp implements GetRestaurantRatingRepo {
    private final FirebaseFirestore firebaseFirestore;

    public GetRestaurantRatingsRepoImp(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public Observable<MyResult<List<Rating>>> getAllRestaurantRatings(String restaurantId) {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firebaseFirestore.collection("Sellers")
                    .document(restaurantId)
                    .collection("Ratings")
                    .addSnapshotListener((v, e) -> {
                        if (e != null) {
                            emitter.onNext(MyResult.error(e));
                            emitter.onComplete();
                        } else {
                            assert v != null;
                            List<Rating> ratings = v.toObjects(Rating.class);
                            emitter.onNext(MyResult.success(ratings));
                        }
                    });
        });
    }
}

package com.example.logisticcavan.restaurants.data;

import com.example.logisticcavan.common.MyResult;
import com.example.logisticcavan.restaurants.domain.GetRestaurantDataRepo;
import com.example.logisticcavan.restaurants.domain.Restaurant;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class GetRestaurantRepoImp implements GetRestaurantDataRepo {
    private final FirebaseFirestore firestore;
    public GetRestaurantRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }
    @Override
    public Observable<MyResult<Restaurant>> getRestaurant(String restaurantId) {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firestore.collection("Sellers").document(restaurantId).get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
                    if (restaurant != null) {
                        restaurant.setRestaurantId(documentSnapshot.getId());
                        emitter.onNext(MyResult.success(restaurant));
                    }
                }
            }).addOnFailureListener(e -> {
                emitter.onNext(MyResult.error(e));
            });
        });
    }

    @Override
    public Observable<MyResult<List<Restaurant>>> getRestaurantsWithIds(List<String> restaurantIds) {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firestore.collection("Sellers").whereIn(FieldPath.documentId(), restaurantIds).get().addOnSuccessListener(queryDocumentSnapshots -> {
                if (queryDocumentSnapshots!=null){
                    List<Restaurant> restaurants = queryDocumentSnapshots.toObjects(Restaurant.class);
                    emitter.onNext(MyResult.success(restaurants));
                }else {
                    emitter.onNext(MyResult.error(new Exception("No restaurants found")));
                    emitter.onComplete();
                }
            });
        });
    }
}

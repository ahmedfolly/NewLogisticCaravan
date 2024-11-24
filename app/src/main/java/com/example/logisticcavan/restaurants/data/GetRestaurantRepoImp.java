package com.example.logisticcavan.restaurants.data;

import android.util.Log;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.restaurants.domain.GetRestaurantDataRepo;
import com.example.logisticcavan.restaurants.domain.Restaurant;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class GetRestaurantRepoImp implements GetRestaurantDataRepo {
    private final FirebaseFirestore firestore;

    public GetRestaurantRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Single<Restaurant> getRestaurant(String restaurantId) {
        return Single.create(emitter -> {
            firestore.collection("Sellers").document(restaurantId).get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
                    if (restaurant != null) {
                        restaurant.setRestaurantId(documentSnapshot.getId());
                        emitter.onSuccess(restaurant);
                    }
                }
            }).addOnFailureListener(e -> {

            });
        });
    }

    @Override
    public Observable<MyResult<List<Restaurant>>> getRestaurantsWithIds(List<String> restaurantIds) {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firestore.collection("Sellers").whereIn(FieldPath.documentId(), restaurantIds).get().addOnSuccessListener(queryDocumentSnapshots -> {
                if (queryDocumentSnapshots != null) {
                    List<Restaurant> restaurants = queryDocumentSnapshots.toObjects(Restaurant.class);
                    emitter.onNext(MyResult.success(restaurants));
                } else {
                    emitter.onNext(MyResult.error(new Exception("No restaurants found")));
                    emitter.onComplete();
                }
            });
        });
    }

    @Override
    public Observable<MyResult<List<Restaurant>>> getRestaurants() {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firestore.collection("Sellers").addSnapshotListener((queryDocumentSnapshots, e) -> {
                if (e != null) {
                    emitter.onNext(MyResult.error(e));
                    emitter.onComplete();
                    return;
                }
                if (queryDocumentSnapshots != null) {
                    List<Restaurant> restaurants = queryDocumentSnapshots.toObjects(Restaurant.class);
                    for (int i = 0; i < restaurants.size(); i++) {
                        Restaurant restaurant = restaurants.get(i);
                        String documentId = queryDocumentSnapshots.getDocuments().get(i).getId();
                        restaurant.setRestaurantId(documentId);
                        Log.d("TAG", restaurant.getRestaurantId());
                    }
                    emitter.onNext(MyResult.success(restaurants));
                }
            });
        });
    }

    @Override
    public Observable<MyResult<List<String>>> getRestaurantProductsIds(String restaurantId) {
        List<String> productIds = new ArrayList<>();
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firestore.collection("Sellers").document(restaurantId).collection("Products").addSnapshotListener((queryDocumentSnapshots, e) -> {
                if (e != null) {
                    emitter.onNext(MyResult.error(e));
                    emitter.onComplete();
                    return;
                }
                if (queryDocumentSnapshots != null) {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                        String productId = documentSnapshot.getString("productId");
                        if (productId != null) {
                            productIds.add(productId);
                        }
                    }
                    emitter.onNext(MyResult.success(productIds));
                }
            });
        });
    }
}

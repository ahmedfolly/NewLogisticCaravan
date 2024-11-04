package com.example.logisticcavan.products.recommendations.data;

import android.util.Log;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.products.recommendations.domain.GetRecommendationsRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.core.Observable;

public class GetRecommendationsRepoImp implements GetRecommendationsRepo {
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;

    public GetRecommendationsRepoImp(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
        //test categories from firestore
    }

    @Override
    public Observable<MyResult<List<Product>>> getRecommendations() {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            getTopCategories(topCategories ->
                    {
                        if (topCategories != null) {
                            firebaseFirestore.collection("Products")
                                    .whereIn("productCategory", topCategories)
                                    .addSnapshotListener((v, e) -> {
                                        if (e != null) {
                                            emitter.onNext(MyResult.error(e));
                                            emitter.onComplete();
                                        } else {
                                            assert v != null;
                                            List<Product> products = v.toObjects(Product.class);
                                            Log.d("TAG", "getRecommendations: " + products.size());
                                            emitter.onNext(MyResult.success(products));
                                        }
                                    });
                        } else {
                            emitter.onNext(MyResult.success(new ArrayList<>()));
                        }
                    }
            );
        });
    }

    public void getTopCategories(TopCategoriesCallback callback) {
        List<String> topCategoriesNames = new ArrayList<>();
        rawRecommendations(rawRecommendations -> {
            if (rawRecommendations == null) {
                return;
            }
            List<Map<String, Object>> sortedRecommendations = sortlist(rawRecommendations);
            List<Map<String, Object>> topCategories = sortedRecommendations.size() > 5
                    ? sortedRecommendations.subList(0, 5)
                    : sortedRecommendations;
            for (Map<String, Object> m : topCategories) {
                topCategoriesNames.add((String) m.get("category"));
            }
            callback.onSuccess(topCategoriesNames);
        });
    }

    private List<Map<String, Object>> sortlist(List<Map<String, Object>> rawRecommendations) {
        if (!rawRecommendations.isEmpty()) {
            rawRecommendations.sort((m1, m2) -> {
                long frequency = (long) m1.get("frequency");
                long frequency2 = (long) m2.get("frequency");
                return Long.compare(frequency2, frequency);//descending order
            });
            return rawRecommendations;
        }
        return new ArrayList<>();
    }

    private void rawRecommendations(RawRecommendationsCallback callback) {
        firebaseFirestore.collection("users")
                .document(userEmail())
                .get()
                .addOnSuccessListener(task -> {
                    List<Map<String, Object>> rawRecommendations = (List<Map<String, Object>>) task.get("recommendations");
                    callback.onSuccess(rawRecommendations);
                })
                .addOnFailureListener(e -> {

                });
    }

    private String userEmail() {
        return Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
    }

    interface RawRecommendationsCallback {
        void onSuccess(List<Map<String, Object>> rawRecommendations);
    }

    interface TopCategoriesCallback {
        void onSuccess(List<String> topCategories);
    }
}

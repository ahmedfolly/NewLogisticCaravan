package com.example.logisticcavan.orders.addorder.data;

import static com.example.logisticcavan.common.utils.Constant.ORDERS;

import android.util.Log;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.orders.addorder.domain.AddOrderRepo;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * @noinspection ALL
 */
public class AddOrderRepoImp implements AddOrderRepo {
    private final FirebaseFirestore firestore;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final BehaviorSubject<MyResult<String>> subject = BehaviorSubject.create();

    public AddOrderRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Observable<MyResult<String>> addOrder(Order order) {
        subject.onNext(MyResult.loading());
        firestore.collection(ORDERS)
                .add(getOrderDataToUpload(order))
                .addOnSuccessListener(documentReference -> {
                    String orderId = documentReference.getId();
                    uploadOrderId(orderId).addOnSuccessListener(aVoid -> {
                        uploadRecommendations(order);
                        Log.d("TAG", "addOrder: " + orderId);
                        addOrderToCurrentUser(orderId,getUserEmail());
                        subject.onNext(MyResult.success(orderId));
                    }).addOnFailureListener(e -> {
                        subject.onNext(MyResult.error(e));
                    });
                })
                .addOnFailureListener(e -> {
                    subject.onNext(MyResult.error(e));
                });

        return subject.hide();
    }

    @Override
    public Single<String> addOrderIdToUser(String orderId,List<String> userEmails) {
        return Single.create(
                emitter->{
                    for (String email : userEmails){
                       if (!email.equals(getUserEmail())){
                           addOrderToCurrentUser(orderId,email);
                       }
                    }
                    emitter.onSuccess("Uploaded to all users");
                }
        );
    }

    private Task<Void> uploadOrderId(String id) {
        DocumentReference orderRef = firestore.collection(ORDERS).document(id);
        return orderRef.get().continueWithTask(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    Map<String, Object> gDetailsMap = (Map<String, Object>) documentSnapshot.get("generalDetails");
                    if (gDetailsMap != null) {
                        gDetailsMap.put("orderId", id);
                        // Now update the document with the modified map
                        return orderRef.update("generalDetails", gDetailsMap);
                    }
                }
            }
            return Tasks.forException(new Exception("Failed to update order ID"));
        });
    }

    public void addOrderToCurrentUser(String orderId,String userEmail) {
        Map<String, String> orderIdMap = new HashMap<>();
        orderIdMap.put("orderId", orderId);
        firestore.collection("users")
                .document(userEmail)
                .collection("Orders")
                .add(orderIdMap)
                .addOnSuccessListener(v -> {
                    Log.d("TAG", "addOrderToCurrentUser: order id added to curr user");
                })
                .addOnFailureListener(e -> {
                    Log.d("TAG", "addOrderToCurrentUser: " + e.getMessage());
                });

    }

    private Map<String, Object> getOrderDataToUpload(Order order) {
        Map<String, Object> orderDataToUpload = new HashMap<>();
        orderDataToUpload.put("cartItems", order.getCartItems());
        orderDataToUpload.put("courier", order.getCourier());
        orderDataToUpload.put("deliveryTime", order.getDeliveryTime());
        orderDataToUpload.put("customer", order.getCustomer());
        orderDataToUpload.put("restaurant", order.getRestaurant());
        orderDataToUpload.put("generalDetails", order.getGeneralDetails());
        orderDataToUpload.put("location", order.getLocation());
        orderDataToUpload.put("customers", order.getCustomers());
        return orderDataToUpload;
    }

    private List<Map<String, Object>> getCategoriesFromOrderedItems(List<Map<String, Object>> cartItemsMap) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (Map<String, Object> cartItem : cartItemsMap) {
            String category = (String) cartItem.getOrDefault("category", "");
            if (category != null) {
                frequencyMap.put(category, frequencyMap.getOrDefault(category, 0) + 1);
            }
        }

        List<Map<String, Object>> recommendations = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            Map<String, Object> recMap = new HashMap<>();
            recMap.put("category", entry.getKey());
            recMap.put("frequency", entry.getValue());
            recommendations.add(recMap);
        }
        return recommendations;
    }

    private void uploadRecommendations(Order order) {
        List<Map<String, Object>> cartItemsList = order.getCartItems();
        List<Map<String, Object>> localList = getCategoriesFromOrderedItems(cartItemsList);

        DocumentReference docRef = firestore.collection("users").document(getUserEmail());

        // Step 1: Get existing recommendations
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot snapshot = task.getResult();
                List<Map<String, Object>> recommendationsList = snapshot.get("recommendations") != null
                        ? (List<Map<String, Object>>) snapshot.get("recommendations")
                        : new ArrayList<>();

                // Step 2: Create a frequency map for the local categories
                Map<String, Integer> frequencyMap = new HashMap<>();
                for (Map<String, Object> localItem : localList) {
                    String categoryName = (String) localItem.get("category");
                    int newFrequency = (int) localItem.get("frequency");
                    frequencyMap.put(categoryName, frequencyMap.getOrDefault(categoryName, 0) + newFrequency);
                }

                // Step 3: Update the recommendations list
                for (Map<String, Object> recommendation : recommendationsList) {
                    String existingCategory = (String) recommendation.get("category");
                    if (frequencyMap.containsKey(existingCategory)) {
                        long currentFrequency = (long) recommendation.getOrDefault("frequency", 0);
                        int additionalFrequency = frequencyMap.get(existingCategory);
                        recommendation.put("frequency", currentFrequency + additionalFrequency);
                        frequencyMap.remove(existingCategory); // Remove to prevent duplicate addition
                    }
                }

                // Step 4: Add any new categories that were not in the existing recommendations
                for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
                    Map<String, Object> newRecommendation = new HashMap<>();
                    newRecommendation.put("category", entry.getKey());
                    newRecommendation.put("frequency", entry.getValue());
                    recommendationsList.add(newRecommendation);
                }

                // Step 5: Upload the updated recommendations list
                docRef.set(Collections.singletonMap("recommendations", recommendationsList), SetOptions.merge())
                        .addOnSuccessListener(aVoid -> {
                            // Optional: Handle success
                            Log.d("TAG", "Recommendations successfully updated.");
                        })
                        .addOnFailureListener(e -> {
                            // Optional: Handle failure
                            Log.w("TAG", "Error updating recommendations", e);
                        });

            } else {
                // Handle error when retrieving recommendations
                Log.w("TAG", "Error getting recommendations: ", task.getException());
            }
        });
    }

    private String getUserEmail() {
        return Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
    }
}

package com.example.logisticcavan.sharedcart.data;

import android.util.Log;

import com.google.android.gms.tasks.Tasks;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;
import com.example.logisticcavan.sharedcart.domain.repo.AddSharedCartItemRepo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;

public class AddToSharedCartRepoImp implements AddSharedCartItemRepo {
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth mAuth;

    public AddToSharedCartRepoImp(FirebaseFirestore firebaseFirestore, FirebaseAuth mAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.mAuth = mAuth;
    }

    /**
     * @noinspection unchecked
     */
    @Override
    public Single<MyResult<String>> addToSharedCart(SharedProduct sharedProduct,String restaurantId,String restaurantName) {
        return Single.create(
                emitter -> {
                    firebaseFirestore.collection("SharedCart")
                            .whereArrayContains("userIds", userEmail())
                            .get()
                            .addOnSuccessListener(
                                    v -> {
                                        boolean found = false;
                                        String docId = "";
                                        if (!v.isEmpty()) {
                                            for (DocumentChange doc : v.getDocumentChanges()) {
                                                List<String> userIds = (List<String>) doc.getDocument().get("userIds");
                                                assert userIds != null;
                                                if (userIds.contains(userEmail())) {
                                                    Log.d("TAG", "test: " + doc.getDocument().getId());
                                                    found = true;
                                                    docId = doc.getDocument().getId();
                                                    break;
                                                }
                                            }
                                            if (found) {
                                                //here add product to existing room with document id
                                                addProductsToSharedCart(docId, sharedProduct,emitter)
                                                        .addOnSuccessListener(eV -> {
                                                            emitter.onSuccess(MyResult.success("Product shared"));
                                                        })
                                                        .addOnFailureListener(emitter::onError);
                                            } else {
                                                //here create new room
                                                startNewSharedCartRoom(sharedProduct,emitter,restaurantId,restaurantName)
                                                        .addOnSuccessListener(eV -> {
                                                            emitter.onSuccess(MyResult.success("Group created"));
                                                            Log.d("TAG", "addToSharedCart: safs");
                                                        })
                                                        .addOnFailureListener(e->{
                                                            emitter.onError(e);
                                                            Log.d("TAG", "addToSharedCart: "+e.getMessage());

                                                        });
                                            }
                                        } else {
                                            startNewSharedCartRoom(sharedProduct,emitter,restaurantId,restaurantName)
                                                    .addOnSuccessListener(eV -> {
                                                        emitter.onSuccess(MyResult.success("Group created"));
                                                    })
                                                    .addOnFailureListener(e->{
                                                        emitter.onError(e);
                                                        Log.d("TAG", "addToSharedCart: "+e.getMessage());

                                                    });
                                        }

                                    }
                            ).addOnFailureListener(emitter::onError);
                }
        );
    }

    Task<DocumentReference> startNewSharedCartRoom(SharedProduct sharedProduct,
                                                   SingleEmitter<MyResult<String>> emitter,
                                                   String restaurantId,
                                                   String restaurantName) {
        return firebaseFirestore.collection("SharedCart")
                .add(mapSharedCart(restaurantId,restaurantName))
                .addOnSuccessListener(v -> {
                    String sharedCartDocId = v.getId();
                    addProductsToSharedCart(sharedCartDocId, sharedProduct,emitter);
                });
    }

    private Map<String, Object> mapSharedCart(String restaurantId,String restaurantName) {
        Map<String, Object> map = new HashMap<>();
        map.put("adminId", userEmail());
        map.put("userIds", initialUsersList());
        map.put("createdAt", Timestamp.now());
        map.put("restaurantId",restaurantId);
        map.put("restaurantName",restaurantName);
        return map;
    }

    private String userEmail() {
        return Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
    }

    //    private Task<DocumentReference> addProductsToSharedCart(String sharedCartDocId, SharedProduct sharedProduct) {
//        return firebaseFirestore.collection("SharedCart")
//                .document(sharedCartDocId)
//                .collection("SharedProducts")
//                .add(mapSharedProduct(sharedProduct));
//    }
    private Task<Void> addProductsToSharedCart(String sharedCartDocId,
                                               SharedProduct sharedProduct,
                                               SingleEmitter<MyResult<String>> emitter) {
        return firebaseFirestore.collection("SharedCart")
                .document(sharedCartDocId)
                .collection("SharedProducts")
                .whereEqualTo("productId", sharedProduct.getProductId())
                .get()
                .continueWithTask(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        Log.d("TAG", "Product already exists in the shared cart: " + sharedProduct.getProductId());
                        emitter.onSuccess(MyResult.success("Product already exists in the shared cart"));
                        return Tasks.forResult(null); // Return null or handle as needed
                    } else {
                        return firebaseFirestore.collection("SharedCart")
                                .document(sharedCartDocId)
                                .collection("SharedProducts")
                                .add(mapSharedProduct(sharedProduct))
                                .continueWithTask(addTask -> {
                                    if (addTask.isSuccessful()) {
                                        Log.d("TAG", "Product added to shared cart: " + sharedProduct.getProductId());
                                        return Tasks.forResult(null); // Successfully added
                                    } else {
                                        throw Objects.requireNonNull(addTask.getException()); // Propagate any error
                                    }
                                });
                    }
                });
    }

    private Map<String, Object> mapSharedProduct(SharedProduct sharedProduct) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", sharedProduct.getProductId());
        map.put("quantity", sharedProduct.getQuantity());
        map.put("addedBy", sharedProduct.getAddedBy());
        return map;
    }

    private List<String> initialUsersList() {
        List<String> users = new ArrayList<>();
        users.add(userEmail());
        return users;
    }
}


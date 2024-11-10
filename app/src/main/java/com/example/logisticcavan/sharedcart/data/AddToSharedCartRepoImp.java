package com.example.logisticcavan.sharedcart.data;

import android.util.Log;

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

public class AddToSharedCartRepoImp implements AddSharedCartItemRepo {
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth mAuth;

    public AddToSharedCartRepoImp(FirebaseFirestore firebaseFirestore, FirebaseAuth mAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.mAuth = mAuth;
    }

    /** @noinspection unchecked*/
    @Override
    public Single<MyResult<Boolean>> addToSharedCart( SharedProduct sharedProduct) {
        return Single.create(
                emitter -> {
                    firebaseFirestore.collection("SharedCart")
                            .whereArrayContains("userIds", userEmail())
                            .get()
                            .addOnSuccessListener(
                                    v->{
                                        boolean found = false;
                                        String docId = "";
                                        if (!v.isEmpty()){
                                            for (DocumentChange doc: v.getDocumentChanges()){
                                                List<String> userIds = (List<String>) doc.getDocument().get("userIds");
                                                assert userIds != null;
                                                if (userIds.contains(userEmail())){
                                                    Log.d("TAG", "test: "+doc.getDocument().getId());
                                                    found = true;
                                                    docId = doc.getDocument().getId();
                                                    break;
                                                }
                                            }
                                            if (found){
                                                //here add product to existing room with document id
                                                addProductsToSharedCart(docId, sharedProduct)
                                                        .addOnSuccessListener(eV->{emitter.onSuccess(MyResult.success(true));})
                                                        .addOnFailureListener(emitter::onError);
                                            }
                                            else {
                                                //here create new room
                                                startNewSharedCartRoom(sharedProduct)
                                                        .addOnSuccessListener(eV->{emitter.onSuccess(MyResult.success(true));})
                                                        .addOnFailureListener(emitter::onError);
                                            }
                                        }else{
                                            startNewSharedCartRoom(sharedProduct)
                                                    .addOnSuccessListener(eV->{emitter.onSuccess(MyResult.success(true));})
                                                    .addOnFailureListener(emitter::onError);
                                        }

                                    }
                            ).addOnFailureListener(emitter::onError);
                }
        );
    }
    Task<DocumentReference> startNewSharedCartRoom(SharedProduct sharedProduct){
        return firebaseFirestore.collection("SharedCart")
                .add(mapSharedCart())
                .addOnSuccessListener(v -> {
                    String sharedCartDocId = v.getId();
                    addProductsToSharedCart(sharedCartDocId, sharedProduct);});
    }
    private Map<String, Object> mapSharedCart() {
        Map<String, Object> map = new HashMap<>();
        map.put("adminId", userEmail());
        map.put("userIds", initialUsersList());
        map.put("createdAt", Timestamp.now());
        return map;
    }
    private String userEmail() {
        return Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
    }
    private Task<DocumentReference> addProductsToSharedCart(String sharedCartDocId, SharedProduct sharedProduct) {
        return firebaseFirestore.collection("SharedCart")
                .document(sharedCartDocId)
                .collection("SharedProducts")
                .add(mapSharedProduct(sharedProduct));
    }

    private Map<String, Object> mapSharedProduct(SharedProduct sharedProduct) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", sharedProduct.getProductId());
        map.put("quantity", sharedProduct.getQuantity());
        map.put("addedBy", sharedProduct.getAddedBy());
        return map;
    }
    private List<String> initialUsersList(){
        List<String> users = new ArrayList<>();
        users.add(userEmail());
        return users;
    }
}


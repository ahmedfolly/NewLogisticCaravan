package com.example.logisticcavan.sharedcart.data;

import android.util.Log;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;
import com.example.logisticcavan.sharedcart.domain.model.SharedProductWithSharedCart;
import com.example.logisticcavan.sharedcart.domain.repo.GetSharedProductsRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Observable;

public class GetSharedProductsRepoImp implements GetSharedProductsRepo {
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;

    public GetSharedProductsRepoImp(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Observable<MyResult<SharedProductWithSharedCart>> getSharedProducts() {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firebaseFirestore.collection("SharedCart")
                    .whereArrayContains("userIds", userEmail())
                    .addSnapshotListener((snapshot, error) -> {
                        if (error != null || snapshot == null) {
                            emitter.onError(new Exception("Error fetching shared carts"));
                            return;
                        }

                        for (DocumentChange doc : snapshot.getDocumentChanges()) {
                            String sharedCartId = doc.getDocument().getId();
                            Log.d("TAG", "getSharedProducts: "+sharedCartId);
                            getSharedProductsIds(sharedCartId, emitter);
                        }
                    });
        });
    }

    private void getSharedProductsIds(String sharedCartId,Emitter<MyResult<SharedProductWithSharedCart>> emitter) {
        SharedProductWithSharedCart sharedProductWithSharedCart = new SharedProductWithSharedCart();

        DocumentReference cartRef = firebaseFirestore.collection("SharedCart").document(sharedCartId);

        // Listener for product IDs in the Products subcollection of SharedCart
        cartRef.collection("SharedProducts").addSnapshotListener((snapshot, error) -> {
            if (error != null || snapshot == null) {
                emitter.onError(new Exception("Error fetching product IDs"));
                return;
            }
            List<SharedProduct> sharedProducts = snapshot.toObjects(SharedProduct.class);
            sharedProductWithSharedCart.setSharedProducts(sharedProducts);
            List<String> productIds = new ArrayList<>();
            for (QueryDocumentSnapshot doc : snapshot) {
                String productId = doc.getString("productId");
                if (productId != null) {
                    productIds.add(productId);
                }
            }

            if (!productIds.isEmpty()) {
                getProductsById(productIds, sharedProductWithSharedCart, emitter);
            }
        });
    }

    private void getProductsById(List<String> productIds, SharedProductWithSharedCart sharedProductWithSharedCart, Emitter<MyResult<SharedProductWithSharedCart>> emitter) {
        firebaseFirestore.collection("Products")
                .whereIn(FieldPath.documentId(), productIds)
                .addSnapshotListener((snapshot, error) -> {
                    if (error != null || snapshot == null) {
                        emitter.onError(new Exception("Error fetching product details"));
                        return;
                    }

                    List<Product> products = snapshot.toObjects(Product.class);
                    sharedProductWithSharedCart.setProducts(products); // Assuming setProducts is a list setter

                    // Emit the updated data
                    emitter.onNext(MyResult.success(sharedProductWithSharedCart));
                });
    }

    private String userEmail() {
        return Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
    }
}

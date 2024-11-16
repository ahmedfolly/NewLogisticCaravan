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
import com.google.firebase.firestore.DocumentSnapshot;
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
    public Observable<MyResult<List<SharedProductWithSharedCart>>> getSharedProducts() {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firebaseFirestore.collection("SharedCart")
                    .whereArrayContains("userIds", userEmail())
                    .addSnapshotListener((snapshot, error) -> {
                        if (error != null || snapshot == null) {
                            emitter.onError(new Exception("Error fetching shared carts"));
                            return;
                        }

                        List<SharedProductWithSharedCart> sharedCartList = new ArrayList<>();

                        for (DocumentChange doc : snapshot.getDocumentChanges()) {
                            String sharedCartId = doc.getDocument().getId();
                            Log.d("TAG", "getSharedProducts: " + sharedCartId);
                            // Fetch shared products for each shared cart
                            getSharedProductsIds(sharedCartId, sharedCartList, emitter);
                        }
                    });
        });
    }

    private void getSharedProductsIds(String sharedCartId,
                                      List<SharedProductWithSharedCart> sharedCartList,
                                      Emitter<MyResult<List<SharedProductWithSharedCart>>> emitter) {
        SharedProductWithSharedCart sharedProductWithSharedCart = new SharedProductWithSharedCart();
        DocumentReference cartRef = firebaseFirestore.collection("SharedCart").document(sharedCartId);

        cartRef.collection("SharedProducts").addSnapshotListener((snapshot, error) -> {
            if (error != null || snapshot == null) {
                emitter.onError(new Exception("Error fetching product IDs"));
                return;
            }
//            List<SharedProduct> sharedProducts = snapshot.toObjects(SharedProduct.class);
            List<SharedProduct> sharedProducts = new ArrayList<>();
//            for (SharedProduct sharedProduct:sharedProducts){
//                Log.d("TAG", "getSharedProductsIds: "+sharedProduct.getAddedBy());
//                Log.d("TAG", "getSharedProductsIds: "+sharedProduct.getProductId());
//                Log.d("TAG", "getSharedProductsIds: "+sharedProduct.getQuantity());
//            }

            List<String> productIds = new ArrayList<>();
            for (QueryDocumentSnapshot doc : snapshot) {
                SharedProduct sharedProduct = doc.toObject(SharedProduct.class);
                sharedProduct.setSharedProductId(doc.getId());
                sharedProducts.add(sharedProduct);
                String productId = doc.getString("productId");
                if (productId != null) {
                    productIds.add(productId);
                }
            }
            sharedProductWithSharedCart.setSharedProducts(sharedProducts);
            if (!productIds.isEmpty()) {
                getProductsById(productIds, sharedProductWithSharedCart, sharedCartList, emitter);
            }
        });
    }

    private void getProductsById(List<String> productIds,
                                 SharedProductWithSharedCart sharedProductWithSharedCart,
                                 List<SharedProductWithSharedCart> sharedCartList,
                                 Emitter<MyResult<List<SharedProductWithSharedCart>>> emitter)
    {
        firebaseFirestore.collection("Products")
                .whereIn(FieldPath.documentId(), productIds)
                .addSnapshotListener((snapshot, error) -> {
                    if (error != null || snapshot == null) {
                        emitter.onError(new Exception("Error fetching product details"));
                        return;
                    }
                    List<Product> products = snapshot.toObjects(Product.class);
                    for (Product product : products) {
                        Log.d("TAG", "getProductsById: "+product.getProductName());
                    }
                    sharedProductWithSharedCart.setProducts(products);

                    // Add the completed SharedProductWithSharedCart to the list only if it's not already present
                    if (!sharedCartList.contains(sharedProductWithSharedCart)) {
                        sharedCartList.add(sharedProductWithSharedCart);
                    }
                    for (SharedProductWithSharedCart sharedProductWithSharedCart1 :sharedCartList){
                        for (Product product :sharedProductWithSharedCart1.getProducts()){
                            Log.d("TAG", "getProductsById: "+product.getProductName());
                        }
                    }
                    // Emit the entire list
                    emitter.onNext(MyResult.success(new ArrayList<>(sharedCartList)));
                });
    }

    private String userEmail() {
        return Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
    }
}

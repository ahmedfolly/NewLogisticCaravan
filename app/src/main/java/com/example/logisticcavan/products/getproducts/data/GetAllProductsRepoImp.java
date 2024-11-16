package com.example.logisticcavan.products.getproducts.data;

import android.util.Log;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.GetProductsRepo;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetAllProductsRepoImp implements GetProductsRepo {
    private final FirebaseFirestore firestore;

    public GetAllProductsRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Observable<MyResult<List<Product>>> getAllProducts(List<String> productsIds) {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firestore.collection("Products")
                    .whereIn(FieldPath.documentId(), productsIds)
                    .addSnapshotListener((value, error) -> {
                        if (error != null) {
                            emitter.onNext(MyResult.error(error));
                            emitter.onComplete();
                        } else {
                            assert value != null;
                            List<Product> products = value.toObjects(Product.class);
//                            setProductId(products, value);
                            emitter.onNext(MyResult.success(products));
                            Log.d("TAG", "getAllProducts: " + products.size());
                        }
                    });

//                    .addSnapshotListener((value, error) -> {
//                if (error != null) {
//                    emitter.onNext(MyResult.error(error));
//                    emitter.onComplete();
//                } else {
//                    assert value != null;
//                    List<Product> products = value.toObjects(Product.class);
//                    emitter.onNext(MyResult.success(products));
//                }
//            });
        });
    }

    @Override
    public Observable<MyResult<Product>> getProductById(String productId) {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firestore.collection("Products").document(productId).get().addOnSuccessListener(v -> {
                Product product = v.toObject(Product.class);
                assert product != null;
                product.setProductID(productId);
                emitter.onNext(MyResult.success(product));
            }).addOnFailureListener(e -> {
                emitter.onNext(MyResult.error(e));
                emitter.onComplete();
            });
        });
    }

//    private void setProductId(List<Product> products, QuerySnapshot value) {
//        List<DocumentSnapshot> documents = value.getDocuments();
//        if (products.size() == documents.size()) {
//            for (int i = 0; i < products.size(); i++) {
//                Product product = products.get(i);
//                String documentId = documents.get(i).getId();
//                Log.d("TAG", "setProductId: " + documentId);
//                product.setProductID(documentId);
//            }
//        } else {
//            Log.e("TAG", "Mismatch between products and document IDs: " + products.size() + " vs " + documents.size());
//        }
//    }
}

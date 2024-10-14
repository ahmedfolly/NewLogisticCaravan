package com.example.logisticcavan.products.getproducts.data;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.GetProductsRepo;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;

public class GetAllProductsRepoImp implements GetProductsRepo {
    private final FirebaseFirestore firestore;

    public GetAllProductsRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Observable<MyResult<List<Product>>> getAllProducts() {
        return Observable.create(emitter -> {
            emitter.onNext(MyResult.loading());
            firestore.collection("Products").addSnapshotListener((value, error) -> {
                if (error != null) {
                    emitter.onNext(MyResult.error(error));
                    emitter.onComplete();
                } else {
                    assert value != null;
                    List<Product> products = value.toObjects(Product.class);
                    emitter.onNext(MyResult.success(products));
                }
            });
        });
    }
}

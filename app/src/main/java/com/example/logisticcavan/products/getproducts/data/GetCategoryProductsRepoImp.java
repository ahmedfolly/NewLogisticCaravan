package com.example.logisticcavan.products.getproducts.data;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.GetCategoryProductsRepo;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetCategoryProductsRepoImp implements GetCategoryProductsRepo {

    private final FirebaseFirestore firestore;

    public GetCategoryProductsRepoImp(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Observable<MyResult<List<Product>>> getProductsByCategoryName(String categoryName) {
        return Observable.create(emitter->{
           emitter.onNext(MyResult.loading());
           firestore.collection("Products")
                   .whereEqualTo("productCategory" , categoryName)
                   .addSnapshotListener((value, error) -> {
                       if (error != null){
                           emitter.onNext(MyResult.error(error));
                           emitter.onComplete();
                       }
                       assert value!=null;
                       List<Product> productsList = value.toObjects(Product.class);
                       emitter.onNext(MyResult.success(productsList));
                   });
        });
    }
}

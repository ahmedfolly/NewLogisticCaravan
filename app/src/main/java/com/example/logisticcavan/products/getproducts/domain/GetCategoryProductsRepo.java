package com.example.logisticcavan.products.getproducts.domain;

import com.example.logisticcavan.common.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GetCategoryProductsRepo {
    Observable<MyResult<List<Product>>> getProductsByCategoryName(String categoryName);
}

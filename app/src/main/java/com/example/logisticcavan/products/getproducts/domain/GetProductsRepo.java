package com.example.logisticcavan.products.getproducts.domain;

import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GetProductsRepo{
    Observable<MyResult<List<Product>>> getAllProducts(List<String> productsIds);
    Observable<MyResult<Product>> getProductById(String productId);
}

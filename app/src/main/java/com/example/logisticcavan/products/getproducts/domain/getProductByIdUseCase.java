package com.example.logisticcavan.products.getproducts.domain;

import com.example.logisticcavan.common.utils.MyResult;

import io.reactivex.rxjava3.core.Observable;

public class getProductByIdUseCase {
    private final GetProductsRepo getProductsRepo;
    public getProductByIdUseCase(GetProductsRepo getProductsRepo) {
        this.getProductsRepo = getProductsRepo;
    }
    public Observable<MyResult<Product>> execute(String productId) {
        return getProductsRepo.getProductById(productId);
    }
}

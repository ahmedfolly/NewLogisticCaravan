package com.example.logisticcavan.products.getproducts.domain;

import com.example.logisticcavan.common.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetProductsUseCase {
    private final GetProductsRepo repo;

    public GetProductsUseCase(GetProductsRepo repo) {
        this.repo = repo;
    }

    public Observable<MyResult<List<Product>>> execute() {

        return repo.getAllProducts();

    }

}

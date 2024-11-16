package com.example.logisticcavan.sharedcart.domain.usecases;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.sharedcart.domain.model.SharedProductWithSharedCart;
import com.example.logisticcavan.sharedcart.domain.repo.GetSharedProductsRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetSharedProductsUseCase {
    private final GetSharedProductsRepo getSharedProductsRepo;
    public GetSharedProductsUseCase(GetSharedProductsRepo getSharedProductsRepo) {
        this.getSharedProductsRepo = getSharedProductsRepo;
    }

    public Observable<MyResult<List<SharedProductWithSharedCart>>> getSharedProducts() {
        return getSharedProductsRepo.getSharedProducts();
    }
}

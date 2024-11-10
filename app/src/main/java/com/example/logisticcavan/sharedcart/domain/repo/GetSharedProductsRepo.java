package com.example.logisticcavan.sharedcart.domain.repo;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.sharedcart.domain.model.SharedProductWithSharedCart;

import io.reactivex.rxjava3.core.Observable;

public interface GetSharedProductsRepo {
    Observable<MyResult<SharedProductWithSharedCart>> getSharedProducts();
}

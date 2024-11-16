package com.example.logisticcavan.sharedcart.domain.repo;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.sharedcart.domain.model.SharedProductWithSharedCart;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GetSharedProductsRepo {
    Observable<MyResult<List<SharedProductWithSharedCart>>> getSharedProducts();
}

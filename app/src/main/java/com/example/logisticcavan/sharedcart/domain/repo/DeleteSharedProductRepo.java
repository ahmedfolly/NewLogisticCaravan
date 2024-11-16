package com.example.logisticcavan.sharedcart.domain.repo;

import io.reactivex.rxjava3.core.Single;

public interface DeleteSharedProductRepo {
    Single<String> deleteSharedProduct(String sharedProductId);
}

package com.example.logisticcavan.sharedcart.domain.repo;

import io.reactivex.rxjava3.core.Single;

public interface DeleteSharedCartRepo {
    Single<Boolean> deleteSharedCart(String sharedCartId);
}

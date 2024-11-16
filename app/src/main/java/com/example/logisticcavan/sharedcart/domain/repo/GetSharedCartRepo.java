package com.example.logisticcavan.sharedcart.domain.repo;

import com.example.logisticcavan.sharedcart.domain.model.SharedCart;

import io.reactivex.rxjava3.core.Single;

public interface GetSharedCartRepo {
    Single<SharedCart> getSharedCartObject();
}

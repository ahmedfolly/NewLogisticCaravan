package com.example.logisticcavan.sharedcart.domain.usecases;

import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.repo.GetSharedCartRepo;

import io.reactivex.rxjava3.core.Single;

public class GetSharedCartUseCase {
    private final GetSharedCartRepo getAdminIdRepo;
    public GetSharedCartUseCase(GetSharedCartRepo getAdminIdRepo) {
        this.getAdminIdRepo = getAdminIdRepo;
    }
    public Single<SharedCart> getSharedCart() {
        return getAdminIdRepo.getSharedCartObject();
    }
}

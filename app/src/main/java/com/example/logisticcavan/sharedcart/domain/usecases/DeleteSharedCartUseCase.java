package com.example.logisticcavan.sharedcart.domain.usecases;

import com.example.logisticcavan.sharedcart.domain.repo.DeleteSharedCartRepo;

import io.reactivex.rxjava3.core.Single;

public class DeleteSharedCartUseCase {
    private final DeleteSharedCartRepo deleteSharedCartRepo;

    public DeleteSharedCartUseCase(DeleteSharedCartRepo deleteSharedCartRepo) {
        this.deleteSharedCartRepo = deleteSharedCartRepo;
    }

    public Single<Boolean> deleteSharedCart(String sharedCartId) {
        return deleteSharedCartRepo.deleteSharedCart(sharedCartId);
    }
}

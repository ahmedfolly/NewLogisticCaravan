package com.example.logisticcavan.sharedcart.domain.usecases;

import com.example.logisticcavan.sharedcart.domain.repo.DeleteSharedProductRepo;

import io.reactivex.rxjava3.core.Single;

public class DeleteSharedCartProductUseCase {
    private final DeleteSharedProductRepo deleteSharedProductRepo;
    public DeleteSharedCartProductUseCase(DeleteSharedProductRepo deleteSharedProductRepo){
        this.deleteSharedProductRepo = deleteSharedProductRepo;
    }
    public Single<String> deleteSharedProduct(String productId){
        return deleteSharedProductRepo.deleteSharedProduct(productId);
    }
}

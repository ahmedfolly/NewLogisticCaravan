package com.example.logisticcavan.cart.domain.usecases;

import com.example.logisticcavan.cart.data.CartDao;
import com.example.logisticcavan.cart.domain.repos.CartRepo;

import io.reactivex.rxjava3.core.Single;

public class DeleteCartItemByIdUseCase {
    private final CartRepo cartRepo;

    public DeleteCartItemByIdUseCase(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }
    public Single<Boolean> deleteItemById(int id) {
        return cartRepo.deleteItemById(id);
    }
}

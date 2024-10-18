package com.example.logisticcavan.cart.domain.usecases;

import com.example.logisticcavan.cart.domain.repos.CartRepo;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class GetCartCountUseCase {
    private final CartRepo cartRepo;

    public GetCartCountUseCase(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public Single<Integer> execute() {
        return cartRepo.getCartItemsCount();
    }
}

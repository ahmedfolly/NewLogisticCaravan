package com.example.logisticcavan.cart.domain.usecases;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.domain.repos.CartRepo;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class AddToCartUseCase {
    private final CartRepo cartRepo;

    public AddToCartUseCase(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public Single<Boolean> execute(CartItem cartItem) {
        return cartRepo.insert(cartItem);
    }
}

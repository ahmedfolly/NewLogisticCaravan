package com.example.logisticcavan.cart.domain.usecases;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.domain.repos.CartRepo;

public class AddToCartUseCase {
    private final CartRepo cartRepo;

    public AddToCartUseCase(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public void execute(CartItem cartItem) {
        cartRepo.insert(cartItem);
    }
}

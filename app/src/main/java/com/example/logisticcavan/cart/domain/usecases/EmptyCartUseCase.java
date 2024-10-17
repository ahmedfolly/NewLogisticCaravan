package com.example.logisticcavan.cart.domain.usecases;

import com.example.logisticcavan.cart.domain.repos.CartRepo;

public class EmptyCartUseCase {
    private final CartRepo cartRepo;

    public EmptyCartUseCase(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public void execute() {
        cartRepo.deleteAll();
    }
}

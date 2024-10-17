package com.example.logisticcavan.cart.domain.usecases;

import com.example.logisticcavan.cart.domain.repos.CartRepo;

public class UpdateQuantityUseCase {
    private final CartRepo cartRepo;

    public UpdateQuantityUseCase(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public void execute(int id, int quantity) {
        cartRepo.updateQuantity(id, quantity);
    }
}

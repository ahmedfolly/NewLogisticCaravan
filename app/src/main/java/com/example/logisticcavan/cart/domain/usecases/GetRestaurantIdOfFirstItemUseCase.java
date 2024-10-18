package com.example.logisticcavan.cart.domain.usecases;

import com.example.logisticcavan.cart.domain.repos.CartRepo;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class GetRestaurantIdOfFirstItemUseCase {
    private final CartRepo cartRepo;

    public GetRestaurantIdOfFirstItemUseCase(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public Single<Boolean> execute(String restaurantId) {
        return cartRepo.getRestaurantIdOfFirstItem(restaurantId);
    }
}

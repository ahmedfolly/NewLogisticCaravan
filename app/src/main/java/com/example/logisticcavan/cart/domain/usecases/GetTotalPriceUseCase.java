package com.example.logisticcavan.cart.domain.usecases;

import com.example.logisticcavan.cart.domain.repos.CartRepo;
import com.example.logisticcavan.common.utils.MyResult;

import io.reactivex.rxjava3.core.Observable;

public class GetTotalPriceUseCase {
    private final CartRepo cartRepo;

    public GetTotalPriceUseCase(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public Observable<MyResult<Double>> execute() {
        return cartRepo.getTotalCartPrice();
    }
}

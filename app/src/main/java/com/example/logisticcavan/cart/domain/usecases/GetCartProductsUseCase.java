package com.example.logisticcavan.cart.domain.usecases;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.domain.repos.CartRepo;
import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetCartProductsUseCase {
    private final CartRepo cartRepo;

    public GetCartProductsUseCase(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public Observable<MyResult<List<CartItem>>> execute() {
        return cartRepo.getAllCartItems();
    }
}

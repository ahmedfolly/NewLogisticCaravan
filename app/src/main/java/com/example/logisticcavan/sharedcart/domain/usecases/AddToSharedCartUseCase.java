package com.example.logisticcavan.sharedcart.domain.usecases;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;
import com.example.logisticcavan.sharedcart.domain.repo.AddSharedCartItemRepo;

import io.reactivex.rxjava3.core.Single;

public class AddToSharedCartUseCase {
    private final AddSharedCartItemRepo sharedCartRepository;

    public AddToSharedCartUseCase(AddSharedCartItemRepo sharedCartRepository) {
        this.sharedCartRepository = sharedCartRepository;
    }

    public Single<MyResult<String>> addToSharedCart( SharedProduct sharedProduct,String restaurantId,String restaurantName) {
        return sharedCartRepository.addToSharedCart(sharedProduct,restaurantId,restaurantName);
    }
}

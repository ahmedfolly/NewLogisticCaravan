package com.example.logisticcavan.cart.presentaion;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.domain.usecases.AddToCartUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CartViewModel extends ViewModel {
    private final AddToCartUseCase addToCartUseCase;

    @Inject
    public CartViewModel(AddToCartUseCase addToCartUseCase) {
        this.addToCartUseCase = addToCartUseCase;
    }

    public void addToCart(CartItem cartItem) {
        addToCartUseCase.execute(cartItem);
    }

}

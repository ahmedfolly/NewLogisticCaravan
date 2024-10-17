package com.example.logisticcavan.cart.domain.repos;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface CartRepo {
    void insert(CartItem cartItem);

    Observable<MyResult<List<CartItem>>> getAllCartItems();

    void deleteAll();

    void deleteItemById(int id);

    void updateQuantity(int id, int quantity);

    void updatePrice(int id, double price);

    Observable<MyResult<Double>> getTotalCartPrice();
}

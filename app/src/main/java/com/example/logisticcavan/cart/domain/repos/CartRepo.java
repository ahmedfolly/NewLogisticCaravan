package com.example.logisticcavan.cart.domain.repos;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface CartRepo {
    Single<Boolean> insert(CartItem cartItem);

    Observable<MyResult<List<CartItem>>> getAllCartItems();

    Completable deleteAll();

    void deleteItemById(int id);

    void updateQuantity(int id, int quantity);

    void updatePrice(int id, double price);

    Observable<MyResult<Double>> getTotalCartPrice();

    Single<Boolean> getRestaurantIdOfFirstItem(String restaurantId);

    Single<Integer> getCartItemsCount();
}

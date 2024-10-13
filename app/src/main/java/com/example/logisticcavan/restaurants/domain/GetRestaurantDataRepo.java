package com.example.logisticcavan.restaurants.domain;

import com.example.logisticcavan.common.MyResult;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface GetRestaurantDataRepo {
    Observable<MyResult<Restaurant>> getRestaurant(String restaurantId);
}

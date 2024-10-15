package com.example.logisticcavan.restaurants.domain;

import com.example.logisticcavan.common.MyResult;

import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface GetRestaurantDataRepo {
    Observable<MyResult<Restaurant>> getRestaurant(String restaurantId);
    Observable<MyResult<List<Restaurant>>> getRestaurantsWithIds(List<String> restaurantIds);
}

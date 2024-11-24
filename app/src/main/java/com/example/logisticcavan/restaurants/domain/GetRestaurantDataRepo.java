package com.example.logisticcavan.restaurants.domain;

import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface GetRestaurantDataRepo {
    Single<Restaurant> getRestaurant(String restaurantId);

    Observable<MyResult<List<Restaurant>>> getRestaurantsWithIds(List<String> restaurantIds);

    Observable<MyResult<List<Restaurant>>> getRestaurants();
    Observable<MyResult<List<String>>> getRestaurantProductsIds(String restaurantId);
}

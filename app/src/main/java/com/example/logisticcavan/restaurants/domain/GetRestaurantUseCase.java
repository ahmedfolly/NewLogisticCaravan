package com.example.logisticcavan.restaurants.domain;

import com.example.logisticcavan.common.utils.MyResult;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class GetRestaurantUseCase {
    private final GetRestaurantDataRepo repo;

    public GetRestaurantUseCase(GetRestaurantDataRepo repo) {
        this.repo = repo;
    }

    public Single<Restaurant> execute(String restaurantId){
        return repo.getRestaurant(restaurantId);
    }
}

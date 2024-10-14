package com.example.logisticcavan.restaurants.domain;

import com.example.logisticcavan.common.utils.MyResult;

import io.reactivex.rxjava3.core.Observable;

public class GetRestaurantUseCase {
    private final GetRestaurantDataRepo repo;

    public GetRestaurantUseCase(GetRestaurantDataRepo repo) {
        this.repo = repo;
    }

    public Observable<MyResult<Restaurant>> execute(String restaurantId){
        return repo.getRestaurant(restaurantId);
    }
}

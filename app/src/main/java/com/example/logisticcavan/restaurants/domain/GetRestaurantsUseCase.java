package com.example.logisticcavan.restaurants.domain;

import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetRestaurantsUseCase {
    private final GetRestaurantDataRepo getRestaurantDataRepo;

    public GetRestaurantsUseCase(GetRestaurantDataRepo getRestaurantDataRepo) {
        this.getRestaurantDataRepo = getRestaurantDataRepo;
    }
    public Observable<MyResult<List<Restaurant>>> getRestaurants() {
        return getRestaurantDataRepo.getRestaurants();
    }
}

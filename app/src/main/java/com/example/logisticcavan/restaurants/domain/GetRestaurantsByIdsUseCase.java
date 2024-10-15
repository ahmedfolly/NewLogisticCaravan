package com.example.logisticcavan.restaurants.domain;

import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.core.Observable;

public class GetRestaurantsByIdsUseCase {
    private final GetRestaurantDataRepo repo;
    public GetRestaurantsByIdsUseCase(GetRestaurantDataRepo repo) {
        this.repo = repo;
    }
    public Observable<MyResult<List<Restaurant>>> execute(List<String> restaurantIds) {
        return repo.getRestaurantsWithIds(restaurantIds);
    }
}

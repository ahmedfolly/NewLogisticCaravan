package com.example.logisticcavan.restaurants.domain;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetRestaurantProductsUseCase {
    private final GetRestaurantDataRepo getRestaurantDataRepo;

    public GetRestaurantProductsUseCase(GetRestaurantDataRepo getRestaurantDataRepo) {
        this.getRestaurantDataRepo = getRestaurantDataRepo;
    }

    public Observable<MyResult<List<String>>> execute(String restaurantId) {
        return getRestaurantDataRepo.getRestaurantProductsIds(restaurantId);
    }
}

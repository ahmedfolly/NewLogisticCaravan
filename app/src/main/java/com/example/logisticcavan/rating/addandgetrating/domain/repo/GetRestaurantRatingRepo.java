package com.example.logisticcavan.rating.addandgetrating.domain.repo;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.rating.addandgetrating.domain.model.Rating;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GetRestaurantRatingRepo {
    Observable<MyResult<List<Rating>>> getAllRestaurantRatings(String restaurantId);
}

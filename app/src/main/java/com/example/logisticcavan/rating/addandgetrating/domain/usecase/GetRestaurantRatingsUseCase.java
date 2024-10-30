package com.example.logisticcavan.rating.addandgetrating.domain.usecase;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.rating.addandgetrating.domain.model.Rating;
import com.example.logisticcavan.rating.addandgetrating.domain.repo.GetRestaurantRatingRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetRestaurantRatingsUseCase {
    private final GetRestaurantRatingRepo restaurantRatingRepository;

    public GetRestaurantRatingsUseCase(GetRestaurantRatingRepo restaurantRatingRepository) {
        this.restaurantRatingRepository = restaurantRatingRepository;
    }

    public Observable<MyResult<List<Rating>>> execute(String restaurantId) {
        return restaurantRatingRepository.getAllRestaurantRatings(restaurantId);
    }
}

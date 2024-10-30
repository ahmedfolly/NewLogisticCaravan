package com.example.logisticcavan.rating.addandgetrating.domain.usecase;


import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.rating.addandgetrating.domain.model.Rating;
import com.example.logisticcavan.rating.addandgetrating.domain.repo.AddRateRepo;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AddRateUseCase {
    private final AddRateRepo addRateRepo;

    public AddRateUseCase(AddRateRepo addRateRepo) {
        this.addRateRepo = addRateRepo;
    }

    public BehaviorSubject<MyResult<Boolean>> addRate(String restaurantId,Rating rating) {
        return addRateRepo.addRate(restaurantId,rating);
    }
}

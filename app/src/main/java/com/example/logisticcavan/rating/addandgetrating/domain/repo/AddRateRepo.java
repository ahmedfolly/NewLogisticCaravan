package com.example.logisticcavan.rating.addandgetrating.domain.repo;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.rating.addandgetrating.domain.model.Rating;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public interface AddRateRepo {
    BehaviorSubject<MyResult<Boolean>> addRate(String restaurantId,Rating rating);
}

package com.example.logisticcavan.products.recommendations.domain;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetRecommendationsUseCase {
    private final GetRecommendationsRepo getRecommendationsRepo;

    public GetRecommendationsUseCase(GetRecommendationsRepo getRecommendationsRepo) {
        this.getRecommendationsRepo = getRecommendationsRepo;
    }
    public Observable<MyResult<List<Product>>> getRecommendations(){
        return getRecommendationsRepo.getRecommendations();
    }
}

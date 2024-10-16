package com.example.logisticcavan.offers.domain.usecases;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.offers.domain.repos.GetOffersRepo;
import com.example.logisticcavan.offers.domain.Offer;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GetOffersUseCase {
    private final GetOffersRepo repo;

    public GetOffersUseCase(GetOffersRepo repo) {
        this.repo = repo;
    }
    public Observable<MyResult<List<Offer>>> execute(){
        return repo.getAllOffers();
    }
}

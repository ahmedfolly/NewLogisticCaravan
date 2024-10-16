package com.example.logisticcavan.offers.domain.repos;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.offers.domain.Offer;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GetOffersRepo {
    Observable<MyResult<List<Offer>>> getAllOffers();
}

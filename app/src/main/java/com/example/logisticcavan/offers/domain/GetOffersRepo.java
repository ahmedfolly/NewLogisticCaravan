package com.example.logisticcavan.offers.domain;

import com.example.logisticcavan.common.MyResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GetOffersRepo {
    Observable<MyResult<List<Offer>>> getAllOffers();
}

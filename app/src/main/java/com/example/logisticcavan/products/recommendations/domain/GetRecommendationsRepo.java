package com.example.logisticcavan.products.recommendations.domain;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GetRecommendationsRepo {
    Observable<MyResult<List<Product>>> getRecommendations();
}

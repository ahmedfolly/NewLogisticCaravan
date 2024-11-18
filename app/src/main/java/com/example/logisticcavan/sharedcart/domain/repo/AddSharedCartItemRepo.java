package com.example.logisticcavan.sharedcart.domain.repo;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;

import io.reactivex.rxjava3.core.Single;

public interface AddSharedCartItemRepo {
    Single<MyResult<String>> addToSharedCart( SharedProduct sharedProduct,String restaurantId,String restaurantName);
}

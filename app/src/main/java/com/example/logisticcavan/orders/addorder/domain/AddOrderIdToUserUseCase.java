package com.example.logisticcavan.orders.addorder.domain;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class AddOrderIdToUserUseCase {
    private final AddOrderRepo repo;
    public AddOrderIdToUserUseCase(AddOrderRepo repo) {
        this.repo = repo;
    }
    public Single<String> execute(String orderId,List<String> userEmails) {
        return repo.addOrderIdToUser(orderId,userEmails);
    }
}

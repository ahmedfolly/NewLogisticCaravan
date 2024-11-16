package com.example.logisticcavan.sharedcart.domain.usecases;

import com.example.logisticcavan.sharedcart.domain.repo.AddUserToSharedCartRepo;

import io.reactivex.rxjava3.core.Single;

public class AddUserToSharedCartUseCase {
    private final AddUserToSharedCartRepo sharedCartRepository;
    public AddUserToSharedCartUseCase(AddUserToSharedCartRepo sharedCartRepository) {
        this.sharedCartRepository = sharedCartRepository;
    }

    public Single<String> addUserToSharedCart(String userEmail) {
        return sharedCartRepository.CheckUserExistenceAndAddUserToSharedCart(userEmail);
    }
}

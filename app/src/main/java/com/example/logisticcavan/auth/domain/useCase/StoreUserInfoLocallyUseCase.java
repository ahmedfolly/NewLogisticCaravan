package com.example.logisticcavan.auth.domain.useCase;

import com.example.logisticcavan.auth.data.LocalStorageRepository;
import com.example.logisticcavan.auth.domain.entity.UserInfo;

import javax.inject.Inject;

public class StoreUserInfoLocallyUseCase {

    private final LocalStorageRepository localStorageRepository;

    @Inject
    public StoreUserInfoLocallyUseCase(LocalStorageRepository localStorageRepository) {
        this.localStorageRepository = localStorageRepository;
    }

    public void storeUserInfo(UserInfo userInfo) {
        localStorageRepository.saveUserInfo(userInfo);
    }
}

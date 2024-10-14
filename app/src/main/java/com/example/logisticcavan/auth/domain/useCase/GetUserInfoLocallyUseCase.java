package com.example.logisticcavan.auth.domain.useCase;

import com.example.logisticcavan.auth.data.LocalStorageRepository;
import com.example.logisticcavan.auth.domain.entity.UserInfo;

import javax.inject.Inject;

public class GetUserInfoLocallyUseCase {

    private final LocalStorageRepository localStorageRepository;

    @Inject
    public GetUserInfoLocallyUseCase(LocalStorageRepository localStorageRepository) {
        this.localStorageRepository = localStorageRepository;
    }


    public UserInfo getUserInfo() {
        return localStorageRepository.getUserInfo();
    }
}

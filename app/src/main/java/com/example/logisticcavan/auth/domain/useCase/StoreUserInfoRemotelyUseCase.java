package com.example.logisticcavan.auth.domain.useCase;

import com.example.logisticcavan.auth.data.RemoteStorageRepository;
import com.example.logisticcavan.auth.domain.entity.UserInfo;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class StoreUserInfoRemotelyUseCase {

    private final RemoteStorageRepository remoteStorageRepository;

    @Inject
    public StoreUserInfoRemotelyUseCase(RemoteStorageRepository remoteStorageRepository) {
        this.remoteStorageRepository = remoteStorageRepository;
    }

    public CompletableFuture<Void> storeUserInfo(UserInfo userInfo) {
        return remoteStorageRepository.saveUserInfoToFirebase(userInfo);
    }
}

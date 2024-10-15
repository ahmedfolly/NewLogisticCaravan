package com.example.logisticcavan.auth.domain.useCase;

import com.example.logisticcavan.auth.data.RemoteStorageRepository;
import com.example.logisticcavan.auth.domain.entity.UserInfo;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class GetUserInfoRemotelyUseCase {

    private final RemoteStorageRepository remoteStorageRepository;

    @Inject
    public GetUserInfoRemotelyUseCase(RemoteStorageRepository remoteStorageRepository) {
        this.remoteStorageRepository = remoteStorageRepository;
    }

    public CompletableFuture<UserInfo> getUserInfo(String email) {
        return remoteStorageRepository.getUserInfoFromFirebase(email);
    }
}

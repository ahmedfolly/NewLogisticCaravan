package com.example.logisticcavan.more.domain;

import com.example.logisticcavan.auth.domain.repo.AuthRepository;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class ChangePasswordUseCase {

    private final AuthRepository authRepository;

    @Inject
    public ChangePasswordUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    public CompletableFuture<Void> changePassword(String oldPassword, String newPassword) {
    return authRepository.changePassword(oldPassword, newPassword);
    }
}

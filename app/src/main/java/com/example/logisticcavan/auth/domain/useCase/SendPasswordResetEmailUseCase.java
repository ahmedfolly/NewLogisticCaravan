package com.example.logisticcavan.auth.domain.useCase;

import com.example.logisticcavan.auth.domain.repo.AuthRepository;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class SendPasswordResetEmailUseCase {


    private final AuthRepository authRepository;

    @Inject
    public SendPasswordResetEmailUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public CompletableFuture<Void> sendPasswordResetEmail(String email) {
       return authRepository.sendPasswordResetEmail(email);
    }
}

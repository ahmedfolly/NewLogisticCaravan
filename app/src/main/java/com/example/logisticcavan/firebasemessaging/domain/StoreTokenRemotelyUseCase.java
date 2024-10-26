package com.example.logisticcavan.firebasemessaging.domain;

import javax.inject.Inject;

public class StoreTokenRemotelyUseCase {

    private final TokenRepository tokenRepository;

    public void execute(String token) {
        tokenRepository.storeToken(token);
    }

    @Inject
    public StoreTokenRemotelyUseCase(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
}

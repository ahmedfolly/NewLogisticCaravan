package com.example.logisticcavan.auth.domain;

import com.google.firebase.auth.AuthResult;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class LoginUseCase  {

    private AuthRepository authRepository;

    @Inject
    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    public CompletableFuture<AuthResult> login(RegistrationData registrationData){
         return  authRepository.logIn(registrationData);
    }
}

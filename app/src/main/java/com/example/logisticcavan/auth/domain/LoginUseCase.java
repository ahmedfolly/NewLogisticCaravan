package com.example.logisticcavan.auth.domain;

import com.google.firebase.auth.AuthResult;

import java.util.concurrent.CompletableFuture;

public class LoginUseCase  {

    private AuthRepository authRepository;

    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    public CompletableFuture<AuthResult> login(RegistrationData registrationData){
         return  authRepository.logIn(registrationData);
    }
}

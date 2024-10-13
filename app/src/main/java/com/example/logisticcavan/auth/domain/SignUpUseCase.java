package com.example.logisticcavan.auth.domain;

import com.google.firebase.auth.AuthResult;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class SignUpUseCase {

    private AuthRepository authRepository;

    @Inject
    public SignUpUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    public CompletableFuture<AuthResult> signUp(RegistrationData registrationData){
      return   authRepository.signUp(registrationData);
    }


}

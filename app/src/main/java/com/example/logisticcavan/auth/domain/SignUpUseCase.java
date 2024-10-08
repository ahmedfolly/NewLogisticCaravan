package com.example.logisticcavan.auth.domain;

import com.google.firebase.auth.AuthResult;

import java.util.concurrent.CompletableFuture;

public class SignUpUseCase {

    private AuthRepository authRepository;

    public SignUpUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    public CompletableFuture<AuthResult> signUp(RegistrationData registrationData){
      return   authRepository.signUp(registrationData);
    }


}

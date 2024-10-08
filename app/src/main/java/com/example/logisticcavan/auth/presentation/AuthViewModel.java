package com.example.logisticcavan.auth.presentation;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.auth.domain.LoginUseCase;
import com.example.logisticcavan.auth.domain.RegistrationData;
import com.example.logisticcavan.auth.domain.SignUpUseCase;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.CompletableFuture;

public class AuthViewModel  extends ViewModel {

    private LoginUseCase loginUseCase;
    private SignUpUseCase signUpUseCase;

    public AuthViewModel(LoginUseCase loginUseCase, SignUpUseCase signUpUseCase) {
        this.loginUseCase = loginUseCase;
        this.signUpUseCase = signUpUseCase;
    }



    CompletableFuture<AuthResult> login(String email, String password){
     return    loginUseCase.login(new RegistrationData(email,password));
    }

    CompletableFuture<AuthResult> signUp(String email, String password){
      return   signUpUseCase.signUp(new RegistrationData(email,password));
    }





}

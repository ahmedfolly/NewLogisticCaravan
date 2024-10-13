package com.example.logisticcavan.auth.presentation;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.auth.domain.LoginUseCase;
import com.example.logisticcavan.auth.domain.RegistrationData;
import com.example.logisticcavan.auth.domain.SignUpUseCase;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel  extends ViewModel {


    private LoginUseCase loginUseCase;
    private SignUpUseCase signUpUseCase;

    @Inject
    public AuthViewModel(LoginUseCase loginUseCase, SignUpUseCase signUpUseCase) {
        this.loginUseCase = loginUseCase;
        this.signUpUseCase = signUpUseCase;
    }




  public   CompletableFuture<AuthResult> login(String email, String password) {

     return    loginUseCase.login(new RegistrationData(email,password));
    }

   public CompletableFuture<AuthResult> signUp(String email, String password){
      return   signUpUseCase.signUp(new RegistrationData(email,password));
    }





}

package com.example.logisticcavan.auth.presentation;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.auth.domain.useCase.LoginUseCase;
import com.example.logisticcavan.auth.domain.entity.RegistrationData;
import com.example.logisticcavan.auth.domain.useCase.SignUpUseCase;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel  extends ViewModel {

    private LoginUseCase loginUseCase;
    private SignUpUseCase signUpUseCase;

    private String typeUser;

    @Inject
    public AuthViewModel(LoginUseCase loginUseCase, SignUpUseCase signUpUseCase) {
        this.loginUseCase = loginUseCase;
        this.signUpUseCase = signUpUseCase;
    }



  public   CompletableFuture<AuthResult> login(String email, String password) {
     return    loginUseCase.login(new RegistrationData(email,password));
    }

   public CompletableFuture<AuthResult> signUp(RegistrationData registrationData){
      return   signUpUseCase.signUp(new RegistrationData(registrationData.getEmail(),registrationData.getPassword()));
    }


    public void setTypeUser(String type) {
        typeUser = type;
    }

    public String getTypeUser() {
        return typeUser;
    }
}

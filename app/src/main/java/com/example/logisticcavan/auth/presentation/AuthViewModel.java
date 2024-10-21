package com.example.logisticcavan.auth.presentation;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.auth.domain.entity.RegistrationData;
import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.auth.domain.useCase.GetUserInfoLocallyUseCase;
import com.example.logisticcavan.auth.domain.useCase.GetUserInfoRemotelyUseCase;
import com.example.logisticcavan.auth.domain.useCase.LoginUseCase;
import com.example.logisticcavan.auth.domain.useCase.SendPasswordResetEmailUseCase;
import com.example.logisticcavan.auth.domain.useCase.SignUpUseCase;
import com.example.logisticcavan.auth.domain.useCase.StoreUserInfoLocallyUseCase;
import com.example.logisticcavan.auth.domain.useCase.StoreUserInfoRemotelyUseCase;
import com.example.logisticcavan.common.base.BaseViewModel;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel  extends BaseViewModel {

    private LoginUseCase loginUseCase;
    private SignUpUseCase signUpUseCase;
    private StoreUserInfoRemotelyUseCase storeUserInfoRemotelyUseCase;
    private StoreUserInfoLocallyUseCase storeUserInfoLocallyUseCase;
    private GetUserInfoRemotelyUseCase getUserInfoRemotelyUseCase;
    private GetUserInfoLocallyUseCase getUserInfoLocallyUseCase;
    private SendPasswordResetEmailUseCase sendPasswordResetEmailUseCase;

    private String typeUser;

    @Inject
    public AuthViewModel(LoginUseCase loginUseCase,
                         SignUpUseCase signUpUseCase,
                         StoreUserInfoRemotelyUseCase storeUserInfoRemotelyUseCase,
                         StoreUserInfoLocallyUseCase storeUserInfoLocallyUseCase,
                         GetUserInfoRemotelyUseCase getUserInfoRemotelyUseCase,
                         SendPasswordResetEmailUseCase sendPasswordResetEmailUseCase,
    GetUserInfoLocallyUseCase getUserInfoLocallyUseCase) {
        this.loginUseCase = loginUseCase;
        this.signUpUseCase = signUpUseCase;
        this.storeUserInfoRemotelyUseCase = storeUserInfoRemotelyUseCase;
        this.storeUserInfoLocallyUseCase = storeUserInfoLocallyUseCase;
        this.getUserInfoRemotelyUseCase = getUserInfoRemotelyUseCase;
        this.sendPasswordResetEmailUseCase = sendPasswordResetEmailUseCase;
        this.getUserInfoLocallyUseCase = getUserInfoLocallyUseCase;
    }

    public CompletableFuture<AuthResult> login(RegistrationData registrationData) {
        return    loginUseCase.login(registrationData);
    }

   public CompletableFuture<AuthResult> signUp(RegistrationData registrationData){
      return   signUpUseCase.signUp(new RegistrationData(registrationData.getEmail(),registrationData.getPassword()));
    }

    public CompletableFuture<Void> storeUserInfoRemotely(UserInfo userInfo) {
        return storeUserInfoRemotelyUseCase.storeUserInfo(userInfo);
    }

    public CompletableFuture<UserInfo> getUserRemotely(String email){
        return getUserInfoRemotelyUseCase.getUserInfo(email);
    }

    public CompletableFuture<Void> sendPasswordResetEmail(String email){
        return sendPasswordResetEmailUseCase.sendPasswordResetEmail(email);
    }

    public void storeUserInfoLocally(UserInfo userInfo) {
        storeUserInfoLocallyUseCase.storeUserInfo(userInfo);
    }


    public void setTypeUser(String type) {
        typeUser = type;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public UserInfo getUserInfoLocally() {
        return getUserInfoLocallyUseCase.getUserInfo();
    }

}

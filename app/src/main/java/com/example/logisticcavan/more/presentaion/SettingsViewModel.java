package com.example.logisticcavan.more.presentaion;

import static java.security.AccessController.getContext;

import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.auth.domain.useCase.GetUserInfoLocallyUseCase;
import com.example.logisticcavan.auth.domain.useCase.StoreUserInfoLocallyUseCase;
import com.example.logisticcavan.auth.domain.useCase.StoreUserInfoRemotelyUseCase;
import com.example.logisticcavan.common.base.BaseViewModel;
import com.example.logisticcavan.more.domain.ChangePasswordUseCase;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel

public class SettingsViewModel extends BaseViewModel {

    private StoreUserInfoLocallyUseCase storeUserInfoLocallyUseCase;
    private GetUserInfoLocallyUseCase getUserInfoLocallyUseCase;
    private ChangePasswordUseCase changePasswordUseCase;
   private StoreUserInfoRemotelyUseCase storeUserInfoRemotelyUseCase;


    @Inject
    public SettingsViewModel(
            StoreUserInfoLocallyUseCase storeUserInfoLocallyUseCase,
            GetUserInfoLocallyUseCase getUserInfoLocallyUseCase,
            ChangePasswordUseCase changePasswordUseCase,
            StoreUserInfoRemotelyUseCase storeUserInfoRemotelyUseCase) {
        this.storeUserInfoLocallyUseCase = storeUserInfoLocallyUseCase;
        this.getUserInfoLocallyUseCase = getUserInfoLocallyUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
        this.storeUserInfoRemotelyUseCase = storeUserInfoRemotelyUseCase;
    }


    public UserInfo getUserInfo() {
        return getUserInfoLocallyUseCase.getUserInfo();
    }

    public void changePassword(String oldPassword, String newPassword) {

        resultChangingPassword( changePasswordUseCase.changePassword(oldPassword, newPassword),newPassword);

    }

    private void resultChangingPassword(CompletableFuture<Void> result,String newPassword) {
        result.thenAccept(aVoid -> {
            updateUserInfo(newPassword);

        }).exceptionally(throwable -> {
            setShowErrorMessage(throwable.getMessage());
            return null;
        });

    }

    private void updateUserInfo(String newPassword) {
        UserInfo userInfo = getUserInfo();
        userInfo.setPassword(newPassword);
        storeUserInfoLocally(userInfo);
        CompletableFuture<Void> result = storeUserInfoRemotely(userInfo);
        resultOfStoring(result);
    }

    private void resultOfStoring(CompletableFuture<Void> result) {
        result.thenAccept(aVoid -> {
         setShowSuccessMessage("Password changed successfully");
        }).exceptionally(ex -> {
            setShowErrorMessage(ex.getMessage());
            return null;
        });


    }

    public CompletableFuture<Void> storeUserInfoRemotely(UserInfo userInfo) {
        return storeUserInfoRemotelyUseCase.storeUserInfo(userInfo);
    }

    public void storeUserInfoLocally(UserInfo userInfo) {
        storeUserInfoLocallyUseCase.storeUserInfo(userInfo);
    }

}

package com.example.logisticcavan.more.di;

import com.example.logisticcavan.auth.domain.repo.AuthRepository;
import com.example.logisticcavan.auth.domain.useCase.GetUserInfoLocallyUseCase;
import com.example.logisticcavan.auth.domain.useCase.StoreUserInfoLocallyUseCase;
import com.example.logisticcavan.auth.domain.useCase.StoreUserInfoRemotelyUseCase;
import com.example.logisticcavan.more.domain.ChangePasswordUseCase;
import com.example.logisticcavan.more.presentaion.SettingsViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SettingsDI {


    @Provides
    @Singleton
    SettingsViewModel provideSettingsViewModel(StoreUserInfoLocallyUseCase storeUserInfoLocallyUseCase,
                                               GetUserInfoLocallyUseCase getUserInfoLocallyUseCase,
                                               ChangePasswordUseCase changePasswordUseCase,
                                               StoreUserInfoRemotelyUseCase storeUserInfoRemotelyUseCase) {
        return new SettingsViewModel(storeUserInfoLocallyUseCase, getUserInfoLocallyUseCase, changePasswordUseCase,storeUserInfoRemotelyUseCase);
    }

    @Provides
    ChangePasswordUseCase provideChangePasswordUseCase(AuthRepository authRepository) {
        return new ChangePasswordUseCase(authRepository);
    }
}

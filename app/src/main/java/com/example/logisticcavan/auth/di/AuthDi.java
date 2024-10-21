package com.example.logisticcavan.auth.di;

import android.content.SharedPreferences;

import com.example.logisticcavan.auth.data.AuthRepoImp;
import com.example.logisticcavan.auth.data.LocalStorageRepository;
import com.example.logisticcavan.auth.data.RemoteStorageRepository;
import com.example.logisticcavan.auth.domain.repo.AuthRepository;
import com.example.logisticcavan.auth.domain.useCase.GetUserInfoLocallyUseCase;
import com.example.logisticcavan.auth.domain.useCase.GetUserInfoRemotelyUseCase;
import com.example.logisticcavan.auth.domain.useCase.LoginUseCase;
import com.example.logisticcavan.auth.domain.useCase.SendPasswordResetEmailUseCase;
import com.example.logisticcavan.auth.domain.useCase.SignUpUseCase;
import com.example.logisticcavan.auth.domain.useCase.StoreUserInfoLocallyUseCase;
import com.example.logisticcavan.auth.domain.useCase.StoreUserInfoRemotelyUseCase;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AuthDi {

    @Provides
    @Singleton
    AuthViewModel provideAuthViewModel(
            LoginUseCase loginUseCase,
            SignUpUseCase signUpUseCase,
            StoreUserInfoRemotelyUseCase storeUserInfoRemotelyUseCase,
            StoreUserInfoLocallyUseCase storeUserInfoLocallyUseCase,
            GetUserInfoRemotelyUseCase getUserInfoRemotelyUseCase,
            SendPasswordResetEmailUseCase sendPasswordResetEmailUseCase,
            GetUserInfoLocallyUseCase getUserInfoLocallyUseCase

    ) {
        return new AuthViewModel(loginUseCase,
                signUpUseCase,
                storeUserInfoRemotelyUseCase,
                storeUserInfoLocallyUseCase,
                getUserInfoRemotelyUseCase
                , sendPasswordResetEmailUseCase,
                getUserInfoLocallyUseCase
        );
    }

    //  Use Cases

    @Provides
    LoginUseCase provideLoginUseCase(AuthRepository authRepository
    ) {
        return new LoginUseCase(authRepository);
    }

    @Provides
    SignUpUseCase provideSignUpUseCase(AuthRepository authRepository
    ) {
        return new SignUpUseCase(authRepository);
    }

    @Provides
    StoreUserInfoRemotelyUseCase provideStoreUserInfoRemotelyUseCase(RemoteStorageRepository remoteStorageRepository
    ) {
        return new StoreUserInfoRemotelyUseCase(remoteStorageRepository);
    }

    @Provides
    GetUserInfoRemotelyUseCase provideGetUserInfoRemotelyUseCase(RemoteStorageRepository remoteStorageRepository
    ) {
        return new GetUserInfoRemotelyUseCase(remoteStorageRepository);
    }

    @Provides
    StoreUserInfoLocallyUseCase provideStoreUserInfoLocallyUseCase(LocalStorageRepository localStorageRepository
    ) {
        return new StoreUserInfoLocallyUseCase(localStorageRepository);
    }

    @Provides
    GetUserInfoLocallyUseCase provideGetUserInfoLocallyUseCase(LocalStorageRepository localStorageRepository
    ) {
        return new GetUserInfoLocallyUseCase(localStorageRepository);
    }

    @Provides
    SendPasswordResetEmailUseCase provideSendPasswordResetEmailUseCase(AuthRepository authRepository
    ) {
        return new SendPasswordResetEmailUseCase(authRepository);
    }

    // Repositories
    @Provides
    @Singleton
    RemoteStorageRepository provideRemoteStorageRepository(FirebaseFirestore firebaseFirestore) {
        return new RemoteStorageRepository(firebaseFirestore);
    }

    @Provides
    @Singleton
    LocalStorageRepository provideLocalStorageRepository(SharedPreferences sharedPreferences) {
        return new LocalStorageRepository(sharedPreferences);
    }

    @Provides
    @Singleton
    AuthRepository providesAuthRepository(FirebaseAuth firebaseAuth) {
        return new AuthRepoImp(firebaseAuth);
    }


}

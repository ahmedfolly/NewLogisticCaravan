package com.example.logisticcavan.auth.di;

import android.content.SharedPreferences;

import com.example.logisticcavan.auth.data.AuthRepoImp;
import com.example.logisticcavan.auth.data.LocalStorageRepository;
import com.example.logisticcavan.auth.data.RemoteStorageRepository;
import com.example.logisticcavan.auth.domain.repo.AuthRepository;
import com.example.logisticcavan.auth.domain.useCase.LoginUseCase;
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
            StoreUserInfoLocallyUseCase storeUserInfoLocallyUseCase

    ) {
        return new AuthViewModel(loginUseCase, signUpUseCase, storeUserInfoRemotelyUseCase, storeUserInfoLocallyUseCase);
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
    StoreUserInfoLocallyUseCase provideStoreUserInfoLocallyUseCase(LocalStorageRepository localStorageRepository
    ) {
        return new StoreUserInfoLocallyUseCase(localStorageRepository);
    }



    // Repositories
    @Provides
    RemoteStorageRepository provideRemoteStorageRepository(FirebaseFirestore firebaseFirestore) {
        return new RemoteStorageRepository(firebaseFirestore);
    }

    @Provides
    LocalStorageRepository provideLocalStorageRepository(SharedPreferences sharedPreferences) {
        return new LocalStorageRepository(sharedPreferences);
    }

    @Provides
    @Singleton
    AuthRepository providesAuthRepository(FirebaseAuth firebaseAuth) {
        return new AuthRepoImp(firebaseAuth);
    }


}

package com.example.logisticcavan.auth.di;

import com.example.logisticcavan.auth.data.AuthRepoImp;
import com.example.logisticcavan.auth.domain.AuthRepository;
import com.example.logisticcavan.auth.domain.LoginUseCase;
import com.example.logisticcavan.auth.domain.SignUpUseCase;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.google.firebase.auth.FirebaseAuth;

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
            SignUpUseCase signUpUseCase) {
        return new AuthViewModel(loginUseCase, signUpUseCase);
    }

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
    @Singleton

    AuthRepository providesAuthRepository(FirebaseAuth firebaseAuth) {
        return new AuthRepoImp(firebaseAuth);
    }


}

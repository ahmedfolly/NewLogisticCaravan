package com.example.logisticcavan.firebasemessaging.di;


import com.example.logisticcavan.firebasemessaging.data.TokenRepositoryImp;
import com.example.logisticcavan.firebasemessaging.domain.StoreTokenRemotelyUseCase;
import com.example.logisticcavan.firebasemessaging.domain.TokenRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class MessagingDi {

    @Provides
    public StoreTokenRemotelyUseCase provideStoreTokenRemotelyUseCase(TokenRepository tokenRepository) {
        return new StoreTokenRemotelyUseCase(tokenRepository);
    }

    @Provides
    @Singleton
    public TokenRepository provideTokenRepository(FirebaseFirestore firestore, FirebaseAuth firebaseAuth) {
        return new TokenRepositoryImp(firestore,firebaseAuth);
    }
}

package com.example.logisticcavan.common;

import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class FirebaseDi {
    @Provides
    FirebaseFirestore providesFireStore() {
        return FirebaseFirestore.getInstance();
    }
}

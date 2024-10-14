package com.example.logisticcavan.common.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class FirebaseDi {

    @Provides
    @Singleton
    FirebaseFirestore providesFireStore() {
        return FirebaseFirestore.getInstance();
    }


    @Provides
    @Singleton

    FirebaseAuth providesFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton

    FirebaseUser providesFirebaseUser(FirebaseAuth firebaseAuth){
        return firebaseAuth.getCurrentUser();
    }
}

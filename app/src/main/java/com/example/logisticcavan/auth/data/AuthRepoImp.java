package com.example.logisticcavan.auth.data;

import com.example.logisticcavan.auth.domain.repo.AuthRepository;
import com.example.logisticcavan.auth.domain.entity.RegistrationData;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.CompletableFuture;

public class AuthRepoImp implements AuthRepository {

    private FirebaseAuth firebaseAuth;

    public AuthRepoImp(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public CompletableFuture<AuthResult> logIn(RegistrationData registrationData) {
        CompletableFuture<AuthResult> future = new CompletableFuture<>();

        firebaseAuth.signInWithEmailAndPassword(registrationData.getEmail(), registrationData.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        future.complete(task.getResult());
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }


    @Override
    public CompletableFuture<AuthResult> signUp(RegistrationData registrationData) {
        CompletableFuture<AuthResult> future = new CompletableFuture<>();

        firebaseAuth.createUserWithEmailAndPassword(registrationData.getEmail(), registrationData.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        future.complete(task.getResult());
                    } else {
                        future.completeExceptionally(task.getException());
                    }
        });

        return future;

    }






}

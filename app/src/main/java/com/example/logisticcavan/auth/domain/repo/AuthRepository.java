package com.example.logisticcavan.auth.domain.repo;

import java.util.concurrent.CompletableFuture;

import com.example.logisticcavan.auth.domain.entity.RegistrationData;
import com.google.firebase.auth.AuthResult;

public interface AuthRepository {

     CompletableFuture<AuthResult>  logIn(RegistrationData registrationData);
     CompletableFuture<AuthResult>  signUp(RegistrationData registrationData);
     CompletableFuture<Void>  sendPasswordResetEmail(String email);


    CompletableFuture<Void> changePassword(String oldPassword, String newPassword);
}

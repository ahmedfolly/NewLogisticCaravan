package com.example.logisticcavan.auth.domain;

import java.util.concurrent.CompletableFuture;
import com.google.firebase.auth.AuthResult;

public interface AuthRepository {

     CompletableFuture<AuthResult>  logIn(RegistrationData registrationData);
     CompletableFuture<AuthResult>  signUp(RegistrationData registrationData);
}

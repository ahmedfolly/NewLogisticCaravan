package com.example.logisticcavan.auth.data;

import com.example.logisticcavan.auth.domain.AuthRepository;
import com.example.logisticcavan.auth.domain.RegistrationData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepoImp implements AuthRepository {

    private FirebaseAuth firebaseAuth;
    private Task<AuthResult> taskResult;


    public AuthRepoImp(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void logIn(RegistrationData registrationData) {
        taskResult = firebaseAuth.signInWithEmailAndPassword(registrationData.getEmail(), registrationData.getPassword());
       taskResult.addOnCompleteListener( task -> {

       });
    }

    @Override
    public void signUp(RegistrationData registrationData) {
        taskResult = firebaseAuth.createUserWithEmailAndPassword(registrationData.getEmail(), registrationData.getPassword());
        taskResult.addOnCompleteListener(task -> {

        });
    }
}

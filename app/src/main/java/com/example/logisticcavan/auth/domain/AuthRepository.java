package com.example.logisticcavan.auth.domain;

public interface AuthRepository {

     void logIn(RegistrationData registrationData);
     void signUp(RegistrationData registrationData);
}

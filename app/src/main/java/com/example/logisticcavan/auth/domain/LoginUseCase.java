package com.example.logisticcavan.auth.domain;

public class LoginUseCase  {

    private AuthRepository authRepository;

    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    public void login(RegistrationData registrationData){
        authRepository.logIn(registrationData);
    }
}

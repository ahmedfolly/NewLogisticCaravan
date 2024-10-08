package com.example.logisticcavan.auth.domain;

public class SignUpUseCase {

    private AuthRepository authRepository;

    public SignUpUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    public void signUp(RegistrationData registrationData){
        authRepository.signUp(registrationData);
    }


}

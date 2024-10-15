package com.example.logisticcavan.auth.domain.entity;

final public class RegistrationData {

    private final String email;
    private final String password;

    public RegistrationData(String email ,
                            String password){
        this.email = email;
        this.password = password;
    }

     public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}

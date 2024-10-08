package com.example.logisticcavan.auth.domain;

public class UserInfo {

    private final String id;
    private final String name;
    private final String type;
    private final String email;
    private final String token;

    public UserInfo(String id ,
                    String name ,
                    String type ,
                    String email,
                    String token){
        this.id = id;
        this.name = name;
        this.type = type;
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }
}

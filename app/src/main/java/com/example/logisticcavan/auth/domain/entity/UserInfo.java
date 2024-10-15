package com.example.logisticcavan.auth.domain.entity;

public class UserInfo {

    private  String id = null;
    private  String name = null ;
    private  String type = null;
    private  String email = null;
    private  String token = null;

    public UserInfo() {

    }



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

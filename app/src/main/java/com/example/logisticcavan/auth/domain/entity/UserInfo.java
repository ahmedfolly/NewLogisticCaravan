package com.example.logisticcavan.auth.domain.entity;

public class UserInfo {

    private  String id = null;

    private String firstName = null;
    private String lastName = null;
    private  String type = null;
    private  String email = null;
    private  String token = null;
    private String phone = null;
    private String address = null;
    private  String password = null;
    public UserInfo() {

    }

    public UserInfo(String id ,
                    String firstName,
                    String lastName,
                    String phone,
                    String address,
                    String type ,
                    String email,
                    String token,
                    String password){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.email = email;
        this.token = token;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }


    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return firstName +" "+lastName;
    }
}

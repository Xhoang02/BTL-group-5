package com.example.btl_group5.models;



public class User {
    private String phone;
    private String email;
    private String name;
    private String pass;

    public User(String phone, String email,String pass, String name) {
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
    public String getPass() {
        return pass;
    }

}
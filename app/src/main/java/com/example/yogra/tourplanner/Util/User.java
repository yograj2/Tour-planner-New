package com.example.yogra.tourplanner.Util;

public class User {
    public String name;
    public String email;
    public String password;
    public String conformpassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConformpassword() {
        return conformpassword;
    }

    public void setConformpassword(String conformpassword) {
        this.conformpassword = conformpassword;
    }
    public User(){

    }
    public User(String name,String email,String password,String conformpassword){
        this.name=name;
        this.email=email;
        this.password=password;
        this.conformpassword=conformpassword;
    }
}

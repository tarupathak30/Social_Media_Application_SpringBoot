package com.social_app.request;

public class LoginRequest {
    String email;
    String password;
    public LoginRequest(){

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

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

package com.social_app.response;


import lombok.Getter;

@Getter
public class ApiResponse {
    private String message;
    private boolean status;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }
    public ApiResponse(){

    }
}

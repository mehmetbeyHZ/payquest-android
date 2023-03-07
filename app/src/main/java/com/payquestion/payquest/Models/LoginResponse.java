package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private UserLoginData user;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserLoginData getUser() {
        return user;
    }

    public void setUser(UserLoginData user) {
        this.user = user;
    }

}

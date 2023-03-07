package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginData {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("xp")
    @Expose
    private Integer xp;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }
}

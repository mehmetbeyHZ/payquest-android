package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("balance")
    @Expose
    private Double balance;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("xp")
    @Expose
    private Integer xp;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("ref_code")
    @Expose
    private String ref_code;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("is_verified")
    @Expose
    private int is_verified;
    @SerializedName("diamond")
    @Expose
    private Integer diamond;

    public Integer getDiamond() {
        return diamond;
    }

    public void setDiamond(Integer diamond) {
        this.diamond = diamond;
    }

    public void setIsVerified(int is_verified)
    {
        this.is_verified = is_verified;
    }

    public int getIsVerified()
    {
        return this.is_verified;
    }

    public void setRank(String rank)
    {
        this.rank = rank;
    }

    public String getRank()
    {
        return rank;
    }

    public String getRefCode() {
        return ref_code;
    }

    public void setRefCode(String ref_code) {
        this.ref_code = ref_code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}

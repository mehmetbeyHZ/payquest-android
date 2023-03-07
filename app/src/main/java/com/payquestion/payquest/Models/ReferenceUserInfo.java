package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferenceUserInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("is_verified")
    @Expose
    private Integer isVerified;
    @SerializedName("phone_verified_at")
    @Expose
    private String phoneVerifiedAt;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public String getPhoneVerifiedAt() {
        return phoneVerifiedAt;
    }

    public void setPhoneVerifiedAt(String phoneVerifiedAt) {
        this.phoneVerifiedAt = phoneVerifiedAt;
    }

}

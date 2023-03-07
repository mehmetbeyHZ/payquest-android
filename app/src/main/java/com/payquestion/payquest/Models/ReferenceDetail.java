package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferenceDetail {
    @SerializedName("reference_id")
    @Expose
    private Integer referenceId;
    @SerializedName("from")
    @Expose
    private Integer from;
    @SerializedName("registered_id")
    @Expose
    private Integer registeredId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("user_info")
    @Expose
    private ReferenceUserInfo userInfo;

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getRegisteredId() {
        return registeredId;
    }

    public void setRegisteredId(Integer registeredId) {
        this.registeredId = registeredId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ReferenceUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ReferenceUserInfo userInfo) {
        this.userInfo = userInfo;
    }
}

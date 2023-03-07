package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsRegistered {
    @SerializedName("register_id")
    @Expose
    private Integer registerId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("competition_id")
    @Expose
    private Integer competitionId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user")
    @Expose
    private CompetitionUser user;

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CompetitionUser getUser() {
        return user;
    }

    public void setUser(CompetitionUser user) {
        this.user = user;
    }

}

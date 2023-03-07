package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileInfoDetail {
    @SerializedName("user")
    @Expose
    private UserInfoResponse user;
    @SerializedName("statistic")
    @Expose
    private List<Statistic> statistic = null;

    public UserInfoResponse getUser() {
        return user;
    }

    public void setUser(UserInfoResponse user) {
        this.user = user;
    }

    public List<Statistic> getStatistic() {
        return statistic;
    }

    public void setStatistic(List<Statistic> statistic) {
        this.statistic = statistic;
    }
}

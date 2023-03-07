package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompetitionListResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("competitions")
    @Expose
    private List<Competition> competitions = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }
}

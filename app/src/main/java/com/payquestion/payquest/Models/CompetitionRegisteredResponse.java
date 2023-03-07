package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompetitionRegisteredResponse
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("registered_competitions")
    @Expose
    private List<RegisteredCompetition> registeredCompetitions = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RegisteredCompetition> getRegisteredCompetitions() {
        return registeredCompetitions;
    }

    public void setRegisteredCompetitions(List<RegisteredCompetition> registeredCompetitions) {
        this.registeredCompetitions = registeredCompetitions;
    }
}

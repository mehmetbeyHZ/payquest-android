package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Competition {
    @SerializedName("competition")
    @Expose
    private Integer competition;
    @SerializedName("competition_title")
    @Expose
    private String competitionTitle;
    @SerializedName("competition_description")
    @Expose
    private String competitionDescription;
    @SerializedName("competition_image")
    @Expose
    private String competitionImage;
    @SerializedName("registration_fee")
    @Expose
    private String registrationFee;
    @SerializedName("award")
    @Expose
    private String award;
    @SerializedName("can_register")
    @Expose
    private Integer canRegister;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("last_register_date")
    @Expose
    private String lastRegisterDate;
    @SerializedName("total_winner")
    @Expose
    private Integer totalWinner;
    @SerializedName("max_users")
    @Expose
    private Integer maxUsers;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("total_register_count")
    @Expose
    private Integer totalRegisterCount;
    @SerializedName("is_registered")
    @Expose
    private IsRegistered isRegistered;

    public Integer getCompetition() {
        return competition;
    }

    public void setCompetition(Integer competition) {
        this.competition = competition;
    }

    public String getCompetitionTitle() {
        return competitionTitle;
    }

    public void setCompetitionTitle(String competitionTitle) {
        this.competitionTitle = competitionTitle;
    }

    public String getCompetitionDescription() {
        return competitionDescription;
    }

    public void setCompetitionDescription(String competitionDescription) {
        this.competitionDescription = competitionDescription;
    }

    public String getCompetitionImage() {
        return competitionImage;
    }

    public void setCompetitionImage(String competitionImage) {
        this.competitionImage = competitionImage;
    }

    public String getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(String registrationFee) {
        this.registrationFee = registrationFee;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public Integer getCanRegister() {
        return canRegister;
    }

    public void setCanRegister(Integer canRegister) {
        this.canRegister = canRegister;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getLastRegisterDate() {
        return lastRegisterDate;
    }

    public void setLastRegisterDate(String lastRegisterDate) {
        this.lastRegisterDate = lastRegisterDate;
    }

    public Integer getTotalWinner() {
        return totalWinner;
    }

    public void setTotalWinner(Integer totalWinner) {
        this.totalWinner = totalWinner;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
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

    public Integer getTotalRegisterCount() {
        return totalRegisterCount;
    }

    public void setTotalRegisterCount(Integer totalRegisterCount) {
        this.totalRegisterCount = totalRegisterCount;
    }

    public IsRegistered getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(IsRegistered isRegistered) {
        this.isRegistered = isRegistered;
    }
}

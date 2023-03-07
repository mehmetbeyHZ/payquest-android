package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MissionDetail {
    @SerializedName("mission_id")
    @Expose
    private Integer missionId;
    @SerializedName("mission_level")
    @Expose
    private Integer missionLevel;
    @SerializedName("mission_value")
    @Expose
    private String missionValue;
    @SerializedName("mission_xp")
    @Expose
    private Integer missionXp;
    @SerializedName("is_question")
    @Expose
    private Integer isQuestion;

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public Integer getMissionLevel() {
        return missionLevel;
    }

    public void setMissionLevel(Integer missionLevel) {
        this.missionLevel = missionLevel;
    }

    public String getMissionValue() {
        return missionValue;
    }

    public void setMissionValue(String missionValue) {
        this.missionValue = missionValue;
    }

    public Integer getMissionXp() {
        return missionXp;
    }

    public void setMissionXp(Integer missionXp) {
        this.missionXp = missionXp;
    }

    public Integer getIsQuestion() {
        return isQuestion;
    }

    public void setIsQuestion(Integer isQuestion) {
        this.isQuestion = isQuestion;
    }
}

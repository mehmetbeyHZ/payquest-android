package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("mission_handle_id")
    @Expose
    private Integer missionHandleId;
    @SerializedName("real_mission_id")
    @Expose
    private Integer realMissionId;
    @SerializedName("mission_user")
    @Expose
    private Integer missionUser;
    @SerializedName("is_completed")
    @Expose
    private Integer isCompleted;
    @SerializedName("mission_detail")
    @Expose
    private MissionDetail missionDetail;

    public Integer getMissionHandleId() {
        return missionHandleId;
    }

    public void setMissionHandleId(Integer missionHandleId) {
        this.missionHandleId = missionHandleId;
    }

    public Integer getRealMissionId() {
        return realMissionId;
    }

    public void setRealMissionId(Integer realMissionId) {
        this.realMissionId = realMissionId;
    }

    public Integer getMissionUser() {
        return missionUser;
    }

    public void setMissionUser(Integer missionUser) {
        this.missionUser = missionUser;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public MissionDetail getMissionDetail() {
        return missionDetail;
    }

    public void setMissionDetail(MissionDetail missionDetail) {
        this.missionDetail = missionDetail;
    }
}

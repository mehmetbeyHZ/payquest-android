package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetQuestion {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mission_id")
    @Expose
    private Integer missionId;
    @SerializedName("mission_question")
    @Expose
    private String missionQuestion;
    @SerializedName("mission_answers")
    @Expose
    private List<String> missionAnswers = null;
    @SerializedName("remains_answer_sec")
    @Expose
    private Integer remainsAnswerSec;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("intent_link")
    @Expose
    private String intentLink;
    @SerializedName("scrape_link")
    @Expose
    private String scrapeLink;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIntentLink() {
        return intentLink;
    }

    public void setIntentLink(String intentLink) {
        this.intentLink = intentLink;
    }

    public String getScrapeLink() {
        return scrapeLink;
    }

    public void setScrapeLinkLink(String scrapeLink) {
        this.scrapeLink = scrapeLink;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public String getMissionQuestion() {
        return missionQuestion;
    }

    public void setMissionQuestion(String missionQuestion) {
        this.missionQuestion = missionQuestion;
    }

    public List<String> getMissionAnswers() {
        return missionAnswers;
    }

    public void setMissionAnswers(List<String> missionAnswers) {
        this.missionAnswers = missionAnswers;
    }

    public Integer getRemainsAnswerSec() {
        return remainsAnswerSec;
    }

    public void setRemainsAnswerSec(Integer remainsAnswerSec) {
        this.remainsAnswerSec = remainsAnswerSec;
    }
}

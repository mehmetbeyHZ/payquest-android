package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistic {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("progress")
    @Expose
    private Integer progress;
    @SerializedName("label")
    @Expose
    private String label;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}

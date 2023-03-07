package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppUpdateResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("build_number")
    @Expose
    private Integer buildNumber;
    @SerializedName("lock_app")
    @Expose
    private Integer lockApp;
    @SerializedName("intent_link")
    @Expose
    private String intentLink;
    @SerializedName("advertising_resets")
    @Expose
    private boolean advertising_resets;
    @SerializedName("advertising_seconds")
    @Expose
    private long advertising_seconds;

    public boolean getAdvertisingResets() {
        return advertising_resets;
    }

    public void setAdvertisingResets(boolean advertising_resets) {
        this.advertising_resets = advertising_resets;
    }

    public long getAdvertisingSeconds() {
        return advertising_seconds;
    }

    public void setAdvertisingSeconds(long advertising_seconds) {
        this.advertising_seconds = advertising_seconds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
    }

    public Integer getLockApp() {
        return lockApp;
    }

    public void setLockApp(Integer lockApp) {
        this.lockApp = lockApp;
    }

    public String getIntentLink() {
        return intentLink;
    }

    public void setIntentLink(String intentLink) {
        this.intentLink = intentLink;
    }
}

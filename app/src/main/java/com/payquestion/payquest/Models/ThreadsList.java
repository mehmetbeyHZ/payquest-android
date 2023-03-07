package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class ThreadsList {
    @SerializedName("thread_id")
    @Expose
    private int threadId;
    @SerializedName("thread_title")
    @Expose
    private String threadTitle;
    @SerializedName("thread_user")
    @Expose
    private Integer threadUser;
    @SerializedName("is_closed")
    @Expose
    private Integer isClosed;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("unseen_count")
    @Expose
    private Integer unseenCount;

    public Integer getUnseenCount() {
        return unseenCount;
    }

    public void setUnseenCount(Integer unseenCount) {
        this.unseenCount = unseenCount;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
    }

    public Integer getThreadUser() {
        return threadUser;
    }

    public void setThreadUser(Integer threadUser) {
        this.threadUser = threadUser;
    }

    public Integer getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Integer isClosed) {
        this.isClosed = isClosed;
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
}

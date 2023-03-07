package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThreadsListResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("threads_list")
    @Expose
    private List<ThreadsList> threadsList = null;
    @SerializedName("total_unseen")
    @Expose
    private int totalUnseen;


    public int getTotalUnseen() {
        return totalUnseen;
    }

    public void setTotalUnseen(int totalUnseen) {
        this.totalUnseen = totalUnseen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ThreadsList> getThreadsList() {
        return threadsList;
    }

    public void setThreadsList(List<ThreadsList> threadsList) {
        this.threadsList = threadsList;
    }
}

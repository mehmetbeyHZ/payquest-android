package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThreadMessageResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("thread_messages")
    @Expose
    private List<ThreadMessage> threadMessages = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ThreadMessage> getThreadMessages() {
        return threadMessages;
    }

    public void setThreadMessages(List<ThreadMessage> threadMessages) {
        this.threadMessages = threadMessages;
    }
}

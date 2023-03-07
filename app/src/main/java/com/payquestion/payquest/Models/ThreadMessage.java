package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThreadMessage {
    @SerializedName("message_id")
    @Expose
    private Integer messageId;
    @SerializedName("thread_id")
    @Expose
    private Integer threadId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sender")
    @Expose
    private Integer sender;
    @SerializedName("receiver")
    @Expose
    private Integer receiver;
    @SerializedName("sender_is_admin")
    @Expose
    private Integer senderIsAdmin;
    @SerializedName("is_seen")
    @Expose
    private Integer isSeen;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getSenderIsAdmin() {
        return senderIsAdmin;
    }

    public void setSenderIsAdmin(Integer senderIsAdmin) {
        this.senderIsAdmin = senderIsAdmin;
    }

    public Integer getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(Integer isSeen) {
        this.isSeen = isSeen;
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

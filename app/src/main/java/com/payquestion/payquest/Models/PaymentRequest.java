package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRequest {
    @SerializedName("request_id")
    @Expose
    private Integer requestId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("iban")
    @Expose
    private String iban;
    @SerializedName("bank_id")
    @Expose
    private Integer bankId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("is_confirmed")
    @Expose
    private Integer isConfirmed;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("bank")
    @Expose
    private Bank bank;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Integer isConfirmed) {
        this.isConfirmed = isConfirmed;
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

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}

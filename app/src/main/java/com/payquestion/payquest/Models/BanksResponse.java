package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BanksResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("banks")
    @Expose
    private List<Bank> banks = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }
}

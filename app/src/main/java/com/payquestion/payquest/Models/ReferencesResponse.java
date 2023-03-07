package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferencesResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("references")
    @Expose
    private List<ReferenceDetail> references = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ReferenceDetail> getReferences() {
        return references;
    }

    public void setReferences(List<ReferenceDetail> references) {
        this.references = references;
    }
}

package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestBuilderModel {
    @SerializedName("headers")
    @Expose
    private List<String> headers = null;
    @SerializedName("url")
    @Expose
    private String url;

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

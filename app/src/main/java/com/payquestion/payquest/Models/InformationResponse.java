package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InformationResponse {
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("route")
    @Expose
    private String route;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}

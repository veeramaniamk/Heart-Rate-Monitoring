package com.saveetha.heartrate.profile.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileResponse {
    public InnerProfile getInnerProfile() {
        return innerProfile;
    }
    public ProfileResponse(String status,String message,InnerProfile innerProfile)
    {
        this.status=status;
        this.message=message;
        this.innerProfile=innerProfile;
    }

    public void setInnerProfile(InnerProfile innerProfile) {
        this.innerProfile = innerProfile;
    }

    InnerProfile innerProfile;
    @SerializedName("details")
    private List<InnerProfile> details;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<InnerProfile> getItem() {
        return details;
    }

    public void setItem(List<InnerProfile> details) {
        this.details = details;
    }
}

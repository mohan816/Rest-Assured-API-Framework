package com.au.loyalty.api.request;

import gherkin.deps.com.google.gson.annotations.Expose;
import gherkin.deps.com.google.gson.annotations.SerializedName;

public class profileRequest {
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Role")
    @Expose
    private String role;
    @SerializedName("DOB")
    @Expose
    private String dob;
    @SerializedName("DeviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("DeviceType")
    @Expose
    private String deviceType;

    public Object getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Object getRole() {
        return role;
    }
    public void setRole(String role) {
        this.lastName = role;
    }

    public Object getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    public Object getDeviceToken() {
        return deviceToken;
    }
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Object getDeviceType() {
        return deviceType;
    }
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

}

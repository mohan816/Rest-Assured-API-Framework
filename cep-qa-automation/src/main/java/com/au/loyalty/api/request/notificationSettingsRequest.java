package com.au.loyalty.api.request;

import gherkin.deps.com.google.gson.annotations.Expose;
import gherkin.deps.com.google.gson.annotations.SerializedName;

public class notificationSettingsRequest {

    private String pushNotification[] = {"NewOrder","OrderStatusChanged","OrderCancelled"};
    private String email[] = {"NewOrder","OrderStatusChanged","OrderCancelled"};

    public String[] getPushNotification() {
        return pushNotification;
    }
    public String[] getEmail() {
        return email;
    }
}

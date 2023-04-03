package com.latam.columbia.api.request;

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

package com.latam.columbia.api.request;

import gherkin.deps.com.google.gson.annotations.Expose;
import gherkin.deps.com.google.gson.annotations.SerializedName;

public class credentials {
    @SerializedName("Password")
    @Expose
    private com.latam.columbia.api.request.password password;

    @SerializedName("Value")
    @Expose
    private String value;

    public String getValue() { return value;}
    public void setValue(String value) {
        this.value = value;
    }

    public com.latam.columbia.api.request.password getPassword() { return password;}
    public void setPassword(String value) {
        this.password = password;
        this.value = value;
    }

}

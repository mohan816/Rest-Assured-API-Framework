package com.latam.columbia.api.request;

import gherkin.deps.com.google.gson.annotations.Expose;
import gherkin.deps.com.google.gson.annotations.SerializedName;

public class password {
    @SerializedName("value")
    @Expose
    private String value;

    public Object getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

}

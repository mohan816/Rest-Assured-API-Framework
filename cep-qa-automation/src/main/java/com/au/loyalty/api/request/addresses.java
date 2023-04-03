package com.au.loyalty.api.request;

import gherkin.deps.com.google.gson.annotations.Expose;
import gherkin.deps.com.google.gson.annotations.SerializedName;

public class addresses {
    @SerializedName("default")
    @Expose
    private Boolean defaul;
    @SerializedName("isStore")
    @Expose
    private Boolean isStore;
    @SerializedName("billingType")
    @Expose
    private String billingType;
    @SerializedName("postalCode")
    @Expose
    private int postalCode;

    public Boolean getUserId() {
        return defaul;
    }
    public void setUserId(Boolean defaul) {
        this.defaul = defaul;
    }

    public Boolean getIsStore() {
        return isStore;
    }
    public void setIsStore(Boolean isStore) {
        this.isStore = isStore;
    }

    public String getBillingType() {
        return billingType;
    }
    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public int getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

}

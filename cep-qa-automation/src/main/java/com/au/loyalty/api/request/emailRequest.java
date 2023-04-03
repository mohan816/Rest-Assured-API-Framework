package com.au.loyalty.api.request;

import gherkin.deps.com.google.gson.annotations.Expose;
import gherkin.deps.com.google.gson.annotations.SerializedName;

public class emailRequest {

    @SerializedName("OrderShipped")
    @Expose
    private String orderShipped;
    @SerializedName("OrderStatusChanged")
    @Expose
    private String orderStatusChanged;
    @SerializedName("OrderCancelled")
    @Expose
    private String orderCancelled;

    public Object getNewOrder() {
        return orderShipped;
    }
    public void setNewOrder(String orderShipped) {
        this.orderShipped = orderShipped;
    }

    public Object getOrderStatusChanged() {
        return orderStatusChanged;
    }
    public void setOrderStatusChanged(String orderStatusChanged) {
        this.orderStatusChanged = orderStatusChanged;
    }

    public Object getOrderCancelled() {
        return orderCancelled;
    }
    public void setOrderCancelled(String orderCancelled) {
        this.orderCancelled = orderCancelled;
    }

}

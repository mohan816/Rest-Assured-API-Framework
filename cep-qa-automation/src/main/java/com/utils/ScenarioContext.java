package com.utils;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    public Response previousResponse;
    public Map<String, Object> responseTransactionMap = new HashMap<>();
    private Map<String, String> dataMapForHooks = new HashMap<>();
    private Map<String, String> dataStore = new HashMap<>();

    public ScenarioContext() {
    }

    public Map<String, String> getDataMapForHooks() {
        return dataMapForHooks;
    }

    public void setDataMapForHooks(String key, String value) {
        dataMapForHooks.put(key, value);
    }

    public void addResponseInTransactionMap(String transactionName, Object responseObject) {

        responseTransactionMap.put(transactionName, responseObject);
    }

    public Map<String, Object> getResponseTransactionMap() {
        return responseTransactionMap;
    }

    public Map<String, String> getDataStore() {
        return dataStore;
    }

    public void setDataStore(String key, String value) {
        dataStore.put(key, value);
    }

}

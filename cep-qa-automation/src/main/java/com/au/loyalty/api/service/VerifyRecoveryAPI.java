package com.au.loyalty.api.service;


import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class VerifyRecoveryAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String verifyUrl;

    public VerifyRecoveryAPI(ScenarioContext context) throws Exception {
        this.context = context;
        verifyUrl = getServerUrl() + LoadProperties.getAULoyaltyPropertyValue("verifyRecovery");
        logger.info("Verify Recovery API URL :- " + verifyUrl);
    }

    /**
     * Method to get the Verify Recovery api request body
     * @return Object
     * @throws Exception
     */
    public HashMap<String, String> getVerifyRecoveryRequestBody() throws Exception {
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("username", "");
        bodyMap.put("recoveryToken", "");
        bodyMap.put("code", "");
        System.out.println("Verify Forgot API request body is - " + bodyMap);
        return bodyMap;
    }

    /**
     * Method to get the Verify Recovery API response
     * @return response
     */
    public Response getVerifyRecoveryResponse(HashMap<String, Object> testdata, HashMap<String, Object> resMap) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap = getVerifyRecoveryRequestBody();
        // Looping through the testdata map
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            System.out.println("Verify recovery Request body : "+bodyMap.containsKey(testField));
            switch (testField){
                case "username":
                    if(testVal.equalsIgnoreCase("validvalue")) {
                        bodyMap.put(testField, resMap.get(testField).toString());
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }else if (testVal.equalsIgnoreCase("blank")){
                        bodyMap.put(testField,"");
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }else{
                        bodyMap.put(testField, testVal);
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }
                    break;
                case "code":
                    if(testVal.equalsIgnoreCase("validvalue")) {
                        bodyMap.put(testField, resMap.get(testField).toString());
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }else if (testVal.equalsIgnoreCase("blank")){
                        bodyMap.put(testField,"");
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }else{
                        bodyMap.put(testField, testVal);
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }
                    break;
                case "recoveryToken":
                    if(testVal.equalsIgnoreCase("validvalue")) {
                        bodyMap.put(testField, resMap.get(testField).toString());
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }else if (testVal.equalsIgnoreCase("blank")){
                        bodyMap.put(testField,"");
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }else{
                        bodyMap.put(testField, testVal);
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }
                    break;
            }
        }
        Response response = BaseApi.LoyaltypostAPI(bodyMap, headerMap,verifyUrl);
        logger.info("Resend Email or Forgot Password API response is :- " + response);
        return response;
    }
}
package com.au.loyalty.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResendEmailAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String resendUrl,forgotUrl;

    public ResendEmailAPI(ScenarioContext context) throws Exception {
        this.context = context;
        resendUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("resendEmail");
        logger.info("Resend Email API URL :- " + resendUrl);
        forgotUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("forgotPassword");
        logger.info("Forgot password API URL :- " + forgotUrl);
    }

    /**
     * Method to get the Resend Email api request body
     * @return Object
     * @throws Exception
     */
    public HashMap<String, String> getResendEmailRequestBody() throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("userIdType", LoadProperties.getAULoyaltyPropertyValue("userIdType"));
        headerMap.put("userId", LoadProperties.getAULoyaltyPropertyValue("userId"));
        System.out.println("Resend Email API request header is - " + headerMap);
        return headerMap;
    }

    /**
     * Method to get the ResendEmail API response
     * @return response
     */
    public Response getResendEmailResponse(HashMap<String, Object> testdata) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        HashMap<String, String> profileMap = new HashMap<>();
        ArrayList<String> addressList = new ArrayList<>();
        HashMap<String, Object> addressMap = new HashMap<>();
        bodyMap = getResendEmailRequestBody();
        //profileMap = (HashMap<String, String>) bodyMap.get("profile");
        // Looping through the testdata map
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            System.out.println("Resend Email Request body : "+bodyMap.containsKey(testField));
            if(!testField.equalsIgnoreCase("blank")) {
                bodyMap.put(testField, testVal);
                System.out.println("Updated Resend Email Request body :" + bodyMap);
            }else{
                bodyMap.put(testField,"");
                System.out.println("Updated Resend Email Request body :" + bodyMap);
            }
        }
        Response response = BaseApi.LoyaltypostAPI(bodyMap, headerMap,resendUrl);
        logger.info("Resend Email or Forgot Password API response is :- " + response);
        return response;
    }


}

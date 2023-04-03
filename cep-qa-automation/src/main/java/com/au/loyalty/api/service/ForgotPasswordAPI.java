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

public class ForgotPasswordAPI extends Basecode {
    private ScenarioContext context;
    private ResendEmailAPI resendEmailAPI;
    private Sheet sheet;
    private String apiUrl;

    public ForgotPasswordAPI(ScenarioContext context) throws Exception {
        this.context = context;
        resendEmailAPI = new ResendEmailAPI(context);
        apiUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("forgotPassword");
        logger.info("Resend Email API URL :- " + apiUrl);
    }

    /**
     * Method to get the Forgot Password api request body
     * @return Object
     * @throws Exception
     */
    public HashMap<String, String> getForgotPasswordRequestBody() throws Exception {
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("userIdType", LoadProperties.getAULoyaltyPropertyValue("userIdType"));
        bodyMap.put("username", LoadProperties.getAULoyaltyPropertyValue("userId"));
        System.out.println("Forgot password API request body is - " + bodyMap);
        return bodyMap;
    }

    /**
     * Method to get the Forgot Password API response
     * @return response
     */
    public Response getForgotPasswordResponse(HashMap<String, Object> testdata) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        HashMap<String, String> profileMap = new HashMap<>();
        ArrayList<String> addressList = new ArrayList<>();
        HashMap<String, Object> addressMap = new HashMap<>();
        bodyMap = getForgotPasswordRequestBody();
        // Looping through the testdata map
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            System.out.println("Forgot Request body : "+bodyMap.containsKey(testField));
            if(!testVal.equalsIgnoreCase("blank")) {
                bodyMap.put(testField, testVal);
                System.out.println("Updated Forgot password Request body :" + bodyMap);
            }else{
                bodyMap.put(testField,"");
                System.out.println("Updated Forgot password Request body :" + bodyMap);
            }
        }
        Response response = BaseApi.LoyaltypostAPI(bodyMap,headerMap,apiUrl);
        System.out.println("Forgot Password API response is :- " + response);
        logger.info("Forgot Password API response is :- " + response);
        return response;
    }

}

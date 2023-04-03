package com.au.loyalty.api.service;


import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String apiUrl;
    private RegistrationAPI acctDetails;


    public ResetPasswordAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("resetPassword");
        logger.info("Reset Password API URL :- " + apiUrl);
    }

    /**
     * Method to get the Reset Password api request body
     * @return Object
     * @throws Exception
     */
    public HashMap<String, String> getResetPasswordRequestBody() throws Exception {
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("stateToken", "");
        bodyMap.put("newPassword", "");
        System.out.println("Forgot password API request body is - " + bodyMap);
        return bodyMap;
    }

    /**
     * Method to get the Verify Recovery API response
     * @return response
     */
    public Response getResetPasswordResponse(HashMap<String, Object> testdata, HashMap<String, Object> resMap) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap = getResetPasswordRequestBody();
        // Looping through the testdata map
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            System.out.println("Verify recovery Request body : "+bodyMap.containsKey(testField));
            switch (testField){
                case "stateToken":
                    if(testVal.equalsIgnoreCase("validvalue")) {
                        bodyMap.put(testField, resMap.get(testField).toString());
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }else if (testVal.equalsIgnoreCase("blank")){
                        bodyMap.put(testField,"");
                        System.out.println("Updated Resend Email Request body :" + bodyMap);
                    }else{
                        bodyMap.put(testField, testVal);
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }
                    break;
                case "newPassword":
                    if(testVal.equalsIgnoreCase("ValidValue")) {
                        String newpassword = getAlphaNumericString(10);
                        updateConfigValue("newPassword",newpassword);
                        System.out.println("Updated new password is "+getAULoyaltyPropertyValue("newPassword"));
                        bodyMap.put(testField, newpassword);
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }else if(testVal.equalsIgnoreCase("Blank")){
                        bodyMap.put(testField,"");
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }else{
                        bodyMap.put(testField,testVal);
                        System.out.println("Updated verify recovery Request body :" + bodyMap);
                    }
                    break;
            }
        }
        Response response = BaseApi.LoyaltypostAPI(bodyMap, headerMap,apiUrl);
        logger.info("Resend Email or Forgot Password API response is :- " + response);
        return response;
    }
}

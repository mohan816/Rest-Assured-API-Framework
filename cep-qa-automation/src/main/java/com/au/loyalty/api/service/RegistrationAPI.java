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

public class RegistrationAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String apiUrl;

    public RegistrationAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("registerEndpoint");
        logger.info("Registration API URL :- " + apiUrl);
    }

    /**
     * Method to get the Registrtion api request body from json file
     * @return Object
     * @throws Exception
     */
    public Object getRegistrationRequestBodyFromJson() throws Exception {
        Object registrationJson;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir+getAULoyaltyPropertyValue("registrationJson");
        registrationJson = jsonFileReader(path);
        System.out.println("Registration API request body is - " + registrationJson);
        return registrationJson;
    }

    /**
     * Method to get the Registration API response
     * @return response
     */
    public Response getRegistrationResponse(HashMap<String, Object> testdata) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        HashMap<String, String> profileMap = new HashMap<>();
        ArrayList<String> addressList = new ArrayList<>();
        HashMap<String, Object> addressMap = new HashMap<>();
        bodyMap = (HashMap<String, Object>) getRegistrationRequestBodyFromJson();
        profileMap = (HashMap<String, String>) bodyMap.get("profile");
        // Looping through the testdata map
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            System.out.println("Profile node : "+profileMap.containsKey(testField));
            System.out.println("Addresses node : "+addressMap.containsKey(testField));
            if(!testField.equalsIgnoreCase("blank")) {
                profileMap.put(testField, testVal);
                System.out.println("Updated profile node :" + profileMap);
            }else{
                profileMap.put(testField,"");
                System.out.println("Updated profile node :" + profileMap);
            }
        }
        System.out.println("Updated request body :"+bodyMap);
        //Response response = BaseApi.postAPI(getRegistrationRequestBody(),getRegistrationHeaderAsMap(),apiUrl);
        Response response = BaseApi.LoyaltypostAPI(bodyMap,headerMap,apiUrl);
        logger.info("Extended search response is :- " + response);
        return response;
    }

}


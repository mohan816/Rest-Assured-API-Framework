package com.au.loyalty.api.service;


import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class ContactUSAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String apiUrl;
    public ServiceAPI serviceAPI;

    public ContactUSAPI(ScenarioContext context) throws Exception {
        this.context = context;
        serviceAPI = new ServiceAPI(context);
        apiUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("contactUS");
        logger.info("Contact US API URL :- " + apiUrl);
    }

    /**
     * Method to get the Contact US api request body
     * @return Object
     * @throws Exception
     */
    public HashMap<String, String> getContactUSRequestBody() throws Exception {
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("type", "");
        bodyMap.put("subject", "");
        bodyMap.put("message", "");
        System.out.println("Contact us API request body is - " + bodyMap);
        return bodyMap;
    }

    /**
     * Method to get the Contact US API response
     * @return response
     */
    public Response getContactUSResponse(HashMap<String, Object> testdata) throws Exception {
        HashMap<String, String> bodyMap = new HashMap<>();
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap = (HashMap<String, String>) getSiteIdHeaderAsMap();
        headerMap.put("okta-accesstoken",readAULoyaltyConfigProperty("mailId"));
        bodyMap = getContactUSRequestBody();
        // Looping through the testdata map
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            System.out.println("Contact us Request body : "+bodyMap.containsKey(testField));
            if(!testField.equalsIgnoreCase("blank")) {
                bodyMap.put(testField, testVal);
                System.out.println("Updated Contact us Request body :" + bodyMap);
            }else if(testField.equalsIgnoreCase("blank")){
                bodyMap.put(testField,"");
                System.out.println("Updated Contact us Request body :" + bodyMap);
            }
        }
        Response response = BaseApi.LoyaltypostAPI(bodyMap, headerMap,apiUrl);
        logger.info("Contact us API response is :- " + response);
        return response;
    }

}

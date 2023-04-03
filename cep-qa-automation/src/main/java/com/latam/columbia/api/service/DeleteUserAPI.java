package com.latam.columbia.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeleteUserAPI extends Basecode {
    private ScenarioContext context;
    private String apiUrl;

    public DeleteUserAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getlatamServerUrl()+ readCommonConfigProperty("registerEndpoint");
        logger.info("Registration API URL :- " + apiUrl);
    }
    /**
     * Method to get the Delete  API response
     * @return response
     */
    public Response getDeleteUserResponse(HashMap<String, Object> testdata) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        HashMap<String, String> profileMap = new HashMap<>();
        headerMap.put("siteId", getLatamColumbiaPropertyValue("siteid"));
        headerMap.put("x-api-key", getLatamColumbiaPropertyValue("x-api-key"));
        // Looping through the testdata map
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            if(!testField.equalsIgnoreCase("blank")) {
                bodyMap.put(testField, testVal);
                System.out.println("Updated body node :" + profileMap);
            }else{
                bodyMap.put(testField,"");
                System.out.println("Updated body node :" + profileMap);
            }
        }
        System.out.println("Updated request body :"+bodyMap);
        Response response = BaseApi.delAPI(bodyMap,headerMap,apiUrl);
        logger.info("Extended search response is :- " + response);
        return response;
    }
}

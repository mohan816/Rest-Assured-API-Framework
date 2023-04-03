package com.latam.columbia.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValidateTaxIdAPI extends Basecode {
    private ScenarioContext context;
    private String apiUrl;

    public ValidateTaxIdAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getlatamServerUrl()+ readCommonConfigProperty("validateTaxID");
        logger.info("Registration API URL :- " + apiUrl);
    }

    /**
     * Method to get the Validate TaxID API response
     * @return response
     */
    public Response getValidateTaxIDResponse(HashMap<String, Object> testdata) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        // Looping through the testdata map
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            if(!testField.equalsIgnoreCase("blank")) {
                bodyMap.put(testField, testVal);
                System.out.println("Updated profile node :" + bodyMap);
            }else{
                bodyMap.put(testField,"");
                System.out.println("Updated profile node :" + bodyMap);
            }
        }
        System.out.println("Updated request body :"+bodyMap);
        String apiServer = getAppServerForProject(getProject());
        apiUrl = apiServer+ readCommonConfigProperty("validateTaxID");
        Response response = BaseApi.postAPI(bodyMap,headerMap,apiUrl);
        logger.info("Extended search response is :- " + response);
        return response;
    }
}

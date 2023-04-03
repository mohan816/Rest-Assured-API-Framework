package com.latam.columbia.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class ClearCartAPI extends Basecode {
    private ScenarioContext context;
    private String apiUrl;
    String value = null;

    public ClearCartAPI(ScenarioContext context) throws Exception {
        this.context = context;
        logger.info("Content API URL :- " + apiUrl);
    }

    /**
     * Method to get the Clear Cart  API response
     * @return response
     */
    public Response getClearCartDetails(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> bodyMap = new HashMap<>();
        System.out.println("Site Id as API request header is - " + headerMap);
        for (Map.Entry mapElement : dataMap.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) dataMap.get(testField);
            if(!testField.equalsIgnoreCase("blank")) {
                headerMap.put(testField, testVal);
                System.out.println("Updated profile node :" + headerMap);
            }else{
                headerMap.put(testField,"");
                System.out.println("Updated profile node :" + headerMap);
            }
        }
        value = (String) dataMap.get("orderType");
        apiUrl = getlatamServerUrl()+ readCommonConfigProperty("ClearCart")+value;
        System.out.println("URL - " + apiUrl);
        Response response = BaseApi.delAPI(bodyMap,headerMap,apiUrl);
        logger.info("Response details is :- " + response);
        return response;
    }

}

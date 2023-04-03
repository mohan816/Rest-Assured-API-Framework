package com.au.loyalty.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class GetRewardsAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String apiUrl;
    private String claimedRewardsapiUrl;
    private String myActivityapiUrl;
    public GetRewardsAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("getRewards");
        claimedRewardsapiUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("claimedRewards");
        myActivityapiUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("myActivity");
        logger.info("Content API URL :- " + apiUrl);
    }

    /**
     * Method to get the Content details API response
     * @return response
     */
    public Map<String, String> getRewardsHeaderAsMap(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("siteId", getAULoyaltyPropertyValue("siteid"));
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
        System.out.println("Site Id as API request header is - " + headerMap);
        return headerMap;
    }
    public Response getRewardsDetails(HashMap<String, Object> dataMap) throws Exception {
        Response response = BaseApi.getAPIMap(getRewardsHeaderAsMap(dataMap),apiUrl);
        logger.info("Response details is :- " + response);
        return response;
    }

    public Response getClaimedRewardsDetails(HashMap<String, Object> dataMap) throws Exception {
        Response response = BaseApi.getAPIMap(getRewardsHeaderAsMap(dataMap),claimedRewardsapiUrl);
        logger.info("Response details is :- " + response);
        return response;
    }
    public Response getMyDetails(HashMap<String, Object> dataMap) throws Exception {
        Response response = BaseApi.getAPIMap(getRewardsHeaderAsMap(dataMap),myActivityapiUrl);
        logger.info("Response details is :- " + response);
        return response;
    }
}

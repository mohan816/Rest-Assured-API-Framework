package com.au.loyalty.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class ClaimRewardAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String apiUrl;
    private String burnRewardapiUrl;

    public ClaimRewardAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getServerUrl() + LoadProperties.getAULoyaltyPropertyValue("claimReward");
        burnRewardapiUrl = getServerUrl() + LoadProperties.getAULoyaltyPropertyValue("burnReward");
        logger.info("Content API URL :- " + apiUrl);
    }

    /**
     * Method to get the Content details API response
     *
     * @return response
     */

    public Response getClaimRewardsDetails(HashMap<String, Object> dataMap, HashMap<String, Object> resmap,String testcase) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        HashMap<String, Object> responseMap = new HashMap<>();
        int value = 0;
        headerMap.put("siteId", getAULoyaltyPropertyValue("siteid"));
        for (Map.Entry mapElement : dataMap.entrySet()) {
            String testField = (String) mapElement.getKey();
            String testVal = (String) dataMap.get(testField);
            if (!testField.equalsIgnoreCase("blank")) {
                headerMap.put(testField, testVal);
                System.out.println("Updated profile node :" + headerMap);
            } else {
                headerMap.put(testField, "");
                System.out.println("Updated profile node :" + headerMap);
            }
        }
        System.out.println("Site Id as API request header is - " + headerMap);
        if(testcase.equalsIgnoreCase("inValid-RewardID")){
            String rewardID = getNumericString(2);
            apiUrl = getServerUrl() + LoadProperties.getAULoyaltyPropertyValue("claimReward") + rewardID;
        }else if (testcase.equalsIgnoreCase("Blank-RewardID")){
            apiUrl = getServerUrl() + LoadProperties.getAULoyaltyPropertyValue("claimReward") + "Rewards";
        }
        else {
            apiUrl = getServerUrl() + LoadProperties.getAULoyaltyPropertyValue("claimReward") + resmap.get("rewardId");
        }
        System.out.println("Api URL -" + apiUrl);
        Response response = BaseApi.postAPI(bodyMap, headerMap, apiUrl);
        logger.info("Response details is :- " + response);
        JsonPath jsonPathEvaluator = (JsonPath) response.jsonPath();
        responseMap = jsonPathEvaluator.get();
        if (responseMap.containsKey("coupons")) {
            value = (int) resmap.get("rewardId");
            updaterewardValue("reward", value);
        }
        return response;
    }

    public Response getBurnRewardsDetails(HashMap<String, Object> dataMap, HashMap<String, Object> resmap,String testcase) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        HashMap<String, Object> responseMap = new HashMap<>();
        int value = 0;
        headerMap.put("siteId", getAULoyaltyPropertyValue("siteid"));
        for (Map.Entry mapElement : dataMap.entrySet()) {
            String testField = (String) mapElement.getKey();
            String testVal = (String) dataMap.get(testField);
            if (!testField.equalsIgnoreCase("blank")) {
                headerMap.put(testField, testVal);
                System.out.println("Updated profile node :" + headerMap);
            } else {
                headerMap.put(testField, "");
                System.out.println("Updated profile node :" + headerMap);
            }
            switch (testField) {
                case "couponCode":
                    if (testVal.equalsIgnoreCase("value")) {
                        bodyMap.put(testField, resmap.get("couponCode"));
                        System.out.println("Updated profile node :" + bodyMap);
                    } else {
                        bodyMap.put(testField, "");
                        System.out.println("Updated profile node :" + bodyMap);
                    }
                    break;
            }
        }
        System.out.println("Site Id as API request header is - " + headerMap);
        if(testcase.equalsIgnoreCase("inValid-RewardID")){
            String rewardID = getNumericString(2);
            apiUrl = getServerUrl() + LoadProperties.getAULoyaltyPropertyValue("claimReward") + rewardID;
        }else if (testcase.equalsIgnoreCase("Blank-RewardID")){
            apiUrl = getServerUrl() + LoadProperties.getAULoyaltyPropertyValue("claimReward") + "Rewards";
        }else {
            apiUrl = getServerUrl() + LoadProperties.getAULoyaltyPropertyValue("burnReward") + LoadProperties.getAULoyaltyPropertyValue("reward");
        }
        System.out.println("Api URL -" + apiUrl);
        Response response = BaseApi.LoyaltypostAPI(bodyMap, headerMap, apiUrl);
        logger.info("Response details is :- " + response);
        return response;

    }
}

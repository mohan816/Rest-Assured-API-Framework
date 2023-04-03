package com.latam.columbia.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.ScenarioContext;
import io.restassured.response.Response;

import java.util.HashMap;

public class GetAPI extends Basecode {
    private ScenarioContext context;
    public String project;
    private String apiServer;
    private String apiUrl;

    String value = null;

    public GetAPI(ScenarioContext context) throws Exception {
        this.context = context;
        logger.info("Content API URL :- " + apiUrl);
    }

    /**
     * Method to get products,Reward details,RewardList,GetStoreDetails,
     * Content,Product,DeliveryEstimate,Holidays,OrderHistory,Translation API response
     * @return response
     */
    public Response getAPIResponseDetails(HashMap<String, Object> dataMap , String endpoint) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        String loginWith = getLatamColumbiaPropertyValue("LoginWith");
//        if(System.getProperty("project") == null) {
//            project = getCommonConfigPropertyValue("project");
//        }else{
//            project = System.getProperty("project");
//        }
        headerMap = updateSiteIdxAPIKeyHeaderForProject(getProject());
        apiServer = getAppServerForProject(getProject());
        System.out.println("Site Id as API request header is - " + headerMap);

        headerMap = updateHeaderBasedonTestdata(loginWith, headerMap, dataMap);

        System.out.println("Site Id as API request header is - " + headerMap);
        switch (endpoint) {
            case "GetAccountDetails":
                apiUrl = apiServer+ readCommonConfigProperty("acctDetails");
                break;
            case "GetCart":
                value = (String) dataMap.get("orderType");
                apiUrl = apiServer + readCommonConfigProperty("ClearCart") + value;
                break;
            case "RewardList":
                apiUrl = apiServer+ readCommonConfigProperty("Rewards");
                break;
            case "GetStoreDetails":
                value = (String) dataMap.get("taxId");
                apiUrl = apiServer+ readCommonConfigProperty("getstores")+value;
                break;
            case "Content":
                value = (String) dataMap.get("storeId");
                apiUrl = apiServer+ readCommonConfigProperty("content")+value;
                break;
            case "Product":
                apiUrl = apiServer+ readCommonConfigProperty("Product");
                break;
            case "DeliveryEstimate":
                value = (String) dataMap.get("storeId");
                apiUrl = apiServer+ readCommonConfigProperty("deliveryEstimate")+value;
                break;
            case "Holidays":
                apiUrl = apiServer+ readCommonConfigProperty("HolidaysList");
                break;
            case "OrderHistory":
                value = (String) dataMap.get("orderType");
                apiUrl = apiServer+ readCommonConfigProperty("OrderType")+value;
                break;
            case "Translation":
                apiUrl = apiServer+ readCommonConfigProperty("Translation");
                break;
            case "SalesTarget":
                value = (String) dataMap.get("storeId");
                if(value.equalsIgnoreCase("")){
                    value = getNumericString(4);
                    apiUrl = apiServer+ readCommonConfigProperty("Targets")+"/"+value;
                }
                apiUrl = apiServer+ readCommonConfigProperty("Targets")+"/"+value;
                break;
            case "Faq":
                apiUrl = apiServer+ readCommonConfigProperty("FAQ");
                break;
            case "ActivityList":
                value = (String) dataMap.get("storeId");
                apiUrl = apiServer+ readCommonConfigProperty("Activity")+value;
                break;
            case "BalancePoints":
                value = (String) dataMap.get("storeId");
                apiUrl = apiServer+ readCommonConfigProperty("balancePoints")+value;
                break;
            case "VendorList":
                apiUrl = apiServer+ readCommonConfigProperty("vendorList");
                break;
            case "Favorite":
                apiUrl = apiServer+ readCommonConfigProperty("Favorite");
                break;
            case "SuggestedSKU":
                apiUrl = apiServer+ readCommonConfigProperty("SuggestedSKU");
                break;

        }
        System.out.println("URL - " + apiUrl);
        Response response = BaseApi.getAPIMap(headerMap,apiUrl);
        logger.info("Response details is :- " + response);
        return response;
    }

}

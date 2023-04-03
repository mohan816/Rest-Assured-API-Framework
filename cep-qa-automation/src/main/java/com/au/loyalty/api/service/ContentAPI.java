package com.au.loyalty.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class ContentAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String apiUrl;
    private String FaqapiUrl;
    public ContentAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("content");
        FaqapiUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("Faq");
        logger.info("Content API URL :- " + apiUrl);
    }

    /**
     * Method to get the Content details API response
     * @return response
     */
    public Map<String, String> getcontentHeaderAsMap() throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("siteId", getAULoyaltyPropertyValue("siteid"));
        headerMap.put("x-api-key", getAULoyaltyPropertyValue("x-api-key"));
        headerMap.put("Content-Language", getAULoyaltyPropertyValue("Content-Language"));
        System.out.println("Site Id as API request header is - " + headerMap);
        return headerMap;
    }
    public Response getContentDetails() throws Exception {
        Response response = BaseApi.getAPIMap(getcontentHeaderAsMap(),apiUrl);
        logger.info("Response details is :- " + response);
        return response;
    }
    public Response getFaqDetails() throws Exception {
        Response response = BaseApi.getAPIMap(getcontentHeaderAsMap(),FaqapiUrl);
        logger.info("Response details is :- " + response);
        return response;
    }

}

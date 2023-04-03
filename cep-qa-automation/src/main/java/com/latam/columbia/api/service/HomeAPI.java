package com.latam.columbia.api.service;


import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

public class HomeAPI extends Basecode {
    private ScenarioContext context;
    private String apiUrl;

    public HomeAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getlatamServerUrl()+ LoadProperties.readCommonConfigProperty("Home");
        LoadProperties.logger.info("Content API URL :- " + apiUrl);
    }
    public Map<String, String> getHomeHeaderAsMap() throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("siteId", LoadProperties.getLatamColumbiaPropertyValue("siteid"));
        System.out.println("Site Id as API request header is - " + headerMap);
        return headerMap;
    }

    /**
     * Method to get the Home API response
     * @return response
     */
    public Response getHomeApiDetails() throws Exception {
        Response response = BaseApi.getAPIMap(getHomeHeaderAsMap(),apiUrl);
        LoadProperties.logger.info("Response details is :- " + response);
        return response;
    }
}

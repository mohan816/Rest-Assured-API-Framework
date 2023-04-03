package com.latam.columbia.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PromotionsAPI extends Basecode {
    private ScenarioContext context;
    private String apiUrl;

    public PromotionsAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getlatamServerUrl()+ readCommonConfigProperty("Promotions");
        logger.info("Registration API URL :- " + apiUrl);
    }
    /**
     * Method to get the Promotions API response
     * @return response
     */
    public Response getPromotionsResponse(HashMap<String, Object> testdata) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String) mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            switch (testField) {
                case "okta-accesstoken":
                    if (testVal.equalsIgnoreCase("blank")) {
                        headerMap.put(testField, "");
                    } else {
                        headerMap.put(testField, testVal);
                    }
                    break;
                case "SiteId":
                    if (testVal.equalsIgnoreCase("value")) {
                        headerMap.put(testField, LoadProperties.getLatamColumbiaPropertyValue("siteid"));
                    } else {
                        headerMap.put(testField, "");
                    }
                    break;
                case "storeId":
                    if (testVal.equalsIgnoreCase("blank")) {
                        apiUrl = getlatamServerUrl()+ readCommonConfigProperty("Promotions")+"";
                    } else  {
                        apiUrl = getlatamServerUrl()+ readCommonConfigProperty("Promotions")+testVal;
                    }
                    break;
            }
            System.out.println("Updated change password Request body :" + headerMap);
        }
        Response response = BaseApi.getAPIMap(headerMap,apiUrl);
        logger.info("Extended search response is :- " + response);
        return response;
    }
}

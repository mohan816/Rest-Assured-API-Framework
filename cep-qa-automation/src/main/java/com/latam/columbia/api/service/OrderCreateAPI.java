package com.latam.columbia.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderCreateAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String apiUrl;
    public OrderCreateAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getlatamServerUrl()+ LoadProperties.readCommonConfigProperty("Order");
        logger.info("Content API URL :- " + apiUrl);
    }

    /**
     * Method to get the Order Create api request body from json file
     * @return response
     */
    public Object getAddCartBodyFromJson() throws Exception {
        Object addcartJson;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir + LoadProperties.getLatamColumbiaPropertyValue("OrdreCreatejson");
        addcartJson = jsonFileReader(path);
        System.out.println("Order Create API request body is - " + addcartJson);
        return addcartJson;
    }
    /**
     * Method to get the Order Create API response for Products and Rewards
     * @return response
     * @param dataMap
     * @param
     */
    public Response getOrderCreateDetails(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        String strDlvryDate = null;
        bodyMap = (HashMap<String, Object>) getAddCartBodyFromJson();
        for (Map.Entry mapElement : dataMap.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) dataMap.get(testField);
            if(testField.equalsIgnoreCase("okta-accesstoken") || testField.equalsIgnoreCase("storeId")){
                if(!testVal.equalsIgnoreCase("blank")) {
                    headerMap.put(testField, testVal);
                    System.out.println("Updated profile node :" + headerMap);
                }else{
                    headerMap.put(testField,"");
                    System.out.println("Updated profile node :" + headerMap);
                }
            }
            switch (testField) {
                case "orderType":
                    if(testVal.equalsIgnoreCase("blank")) {
                        bodyMap.put(testField, "");
                        System.out.println("Updated profile node :" + bodyMap);
                    }else {
                        bodyMap.put(testField, testVal);
                        System.out.println("Updated profile node :" + bodyMap);
                    }
                    break;
                case "deliveryDate":
                    if (testVal.equalsIgnoreCase("value")) {
                        bodyMap.put(testField, getFutureDate());
                    } else {
                        bodyMap.put(testField, "");
                    }
                    break;
            }
        }
            //strDlvryDate = context.previousResponse.jsonPath().get("internal.deliveryDate");
            strDlvryDate = context.previousResponse.jsonPath().get("deliveryDate");
            bodyMap.put("estimatedDeliveryDate", strDlvryDate);
            Response response = BaseApi.postAPI(bodyMap, headerMap,apiUrl);
            logger.info("Response details is :- " + response);
            LoadProperties.logger.info("Change password response is :- " + response);
            return response;
    }

    /**
     * Method to get Order Create JSON API response
     * @return response
     */
    public Object getOrderCreateRequestBodyFromJson() throws Exception {
        Object accountDetailsJson;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir+LoadProperties.getLatamColumbiaPropertyValue("accountDetailsJson");
        accountDetailsJson = jsonFileReader(path);
        System.out.println("Order Create API request body is - " + accountDetailsJson);
        return accountDetailsJson;
    }

}

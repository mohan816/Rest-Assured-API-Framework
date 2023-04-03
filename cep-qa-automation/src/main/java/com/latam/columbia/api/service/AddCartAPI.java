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

public class AddCartAPI extends Basecode {
    private ScenarioContext context;
    private String apiUrl;

    public AddCartAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getlatamServerUrl()+ LoadProperties.readCommonConfigProperty("Addcart");
        logger.info("Content API URL :- " + apiUrl);
    }

    /**
     * Method to Get  Add cart and Update cart Body Json to add and Update products and Rewards
     * @return response
     */
    public Object getAddCartBodyFromJson() throws Exception {
        Object addcartJson;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir + LoadProperties.getLatamColumbiaPropertyValue("AddCartjson");
        addcartJson = jsonFileReader(path);
        System.out.println("Change Password API request body is - " + addcartJson);
        return addcartJson;
    }

    /**
     * Method to Get  Add cart and Update cart API response for products and Rewards
     * @return response
     * @param dataMap
     * @param
     */
    public Response getAddCartDetails(HashMap<String, Object> dataMap ,HashMap<String, Object> resMap, String endpoint) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        HashMap<String, String> requestMap = new HashMap<>();
        HashMap<String, Object> responseMap = new HashMap<>();
        Map<String, Integer> requestMapnum = new HashMap<String, Integer>();
        System.out.println("Site Id as API request header is - " + headerMap);
        bodyMap = (HashMap<String, Object>) getAddCartBodyFromJson();
        ArrayList<Object> itemsArr = (ArrayList<Object>) bodyMap.get("items");
        requestMap = (HashMap<String, String>) itemsArr.get(0);
        requestMapnum = (Map<String, Integer>) itemsArr.get(0);
        int value=0;
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
                    if(!testVal.equalsIgnoreCase("blank")) {
                        bodyMap.put(testField, testVal);
                        System.out.println("Updated profile node :" + bodyMap);
                    }else{
                        bodyMap.put(testField, "");
                        System.out.println("Updated profile node :" + bodyMap);
                    }
                    break;
                case "id":
                    if(testVal.equalsIgnoreCase("validvalue")) {
                        System.out.println(requestMap);
                        requestMap.put(testField, resMap.get(testField).toString());
                    }
                    break;
                case "quantity":
                    if(testVal.equalsIgnoreCase("validvalue")) {
                        String quantity = getNumericString(1);
                         value =Integer.parseInt(quantity);
                        requestMapnum.put(testField,value);
                    }
                    break;
            }
        }if(endpoint.equalsIgnoreCase("UpdateCart")){
            Response response = BaseApi.putAPI(bodyMap, headerMap,apiUrl);
            logger.info("Response details is :- " + response);
            LoadProperties.logger.info("Change password response is :- " + response);
            JsonPath jsonPathEvaluator = (JsonPath) response.jsonPath();
            responseMap = jsonPathEvaluator.get();
            if(responseMap.containsKey("items")){
                updatelatamquantityValue("quantity", value);
            }
            return response;
        }else{
            Response response = BaseApi.postAPI(bodyMap, headerMap,apiUrl);
            logger.info("Response details is :- " + response);
            LoadProperties.logger.info("Change password response is :- " + response);
            return response;
        }
    }
}

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

public class StoreImageAPI extends Basecode {
    private ScenarioContext context;
    public ServiceAPI serviceAPI;
    private String apiUrl;
    private String CustomerApiUrl;
    public RegistrationAPI registrationAPI;

    public StoreImageAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getAppServerForProject(getProject()) + readCommonConfigProperty("StoreImage");
        CustomerApiUrl = getAppServerForProject(getProject()) + LoadProperties.readCommonConfigProperty("CustomerImages");
        LoadProperties.logger.info("Resend Email API URL :- " + apiUrl);
    }
    /**
     * Method to get Store Image api request body from json file
     * @return response
     */
    public Object getStoreImageFromJson() throws Exception {
        Object StoreImageJson;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir + LoadProperties.getLatamColumbiaPropertyValue("StoreImageJson");
        StoreImageJson = jsonFileReader(path);
        System.out.println("Store Image API request body is - " + StoreImageJson);
        return StoreImageJson;
    }
    /**
     * Method to get Customer Image api request body from json file
     * @return response
     */

    public Object getCustomerImageFromJson() throws Exception {
        Object customerImageJson;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir + LoadProperties.getLatamColumbiaPropertyValue("CustomerImageJson");
        customerImageJson = jsonFileReader(path);
        System.out.println("Store Image API request body is - " + customerImageJson);
        return customerImageJson;
    }
    /**
     * Method to Get Store Image API response
     * @return response
     * @param dataMap
     * @param
     */
    public Response getStoreImageResponse(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        String LoginWith = getLatamColumbiaPropertyValue("LoginWith");
        bodyMap = (HashMap<String, Object>) getStoreImageFromJson();
        for (Map.Entry mapElement : dataMap.entrySet()) {
            String testField = (String) mapElement.getKey();
            String testVal = (String) dataMap.get(testField);
            if(!testVal.equalsIgnoreCase("")) {
                if(LoginWith.equalsIgnoreCase("accesstoken")){
                    if(testField.equalsIgnoreCase("okta-accesstoken")){
                        headerMap.put(testField, getAULoyaltyPropertyValue("LatamAccessToken"));
                    }else{
                        headerMap.put(testField, testVal);
                    }

                }else{
                    headerMap.put(testField, testVal);
                }

                System.out.println("Updated header node :" + headerMap);
            }else{
                headerMap.put(testField,"");
                System.out.println("Updated header node :" + headerMap);
            }
//            if (testField.equalsIgnoreCase("okta-accesstoken") || testField.equalsIgnoreCase("storeId")) {
//                if (!testVal.equalsIgnoreCase("blank")) {
//                    headerMap.put(testField, testVal);
//                    System.out.println("Updated profile node :" + headerMap);
//                } else {
//                    headerMap.put(testField, "");
//                    System.out.println("Updated profile node :" + headerMap);
//                }
//            }
            switch (testField) {
                case "storeId":
                    if (testVal.equalsIgnoreCase("value")) {
                        bodyMap.put(testField, testVal);
                        System.out.println("Updated profile node :" + bodyMap);
                    } else {
                        bodyMap.put(testField, testVal);
                        System.out.println("Updated profile node :" + bodyMap);
                    }
                    break;
            }

        }
        String apiServer = getAppServerForProject(getProject());
        apiUrl = apiServer+ readCommonConfigProperty("StoreImage");
        Response response = BaseApi.postAPI(bodyMap, headerMap, apiUrl);
        logger.info("Response details is :- " + response);
        LoadProperties.logger.info("Change password response is :- " + response);
        return response;
    }
    /**
     * Method to Get Customer Image API response
     * @return response
     * @param dataMap
     * @param
     */
    public Response getCustomerImageResponse(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        String LoginWith = getLatamColumbiaPropertyValue("LoginWith");
        bodyMap = (HashMap<String, Object>) getCustomerImageFromJson();
        for (Map.Entry mapElement : dataMap.entrySet()) {
            String testField = (String) mapElement.getKey();
            String testVal = (String) dataMap.get(testField);
            if (!testVal.equalsIgnoreCase("")) {
                if (LoginWith.equalsIgnoreCase("accesstoken")) {
                    if (testField.equalsIgnoreCase("okta-accesstoken")) {
                        headerMap.put(testField, getAULoyaltyPropertyValue("LatamAccessToken"));
                    } else {
                        headerMap.put(testField, testVal);
                    }

                } else {
                    headerMap.put(testField, testVal);
                }

                System.out.println("Updated header node :" + headerMap);
            } else {
                headerMap.put(testField, "");
                System.out.println("Updated header node :" + headerMap);
            }
        }
        String apiServer = getAppServerForProject(getProject());
        apiUrl = apiServer+ readCommonConfigProperty("CustomerImages");
        Response response = BaseApi.postAPI(bodyMap, headerMap, CustomerApiUrl);
        logger.info("Response details is :- " + response);
        LoadProperties.logger.info("Change password response is :- " + response);
        return response;
    }


}

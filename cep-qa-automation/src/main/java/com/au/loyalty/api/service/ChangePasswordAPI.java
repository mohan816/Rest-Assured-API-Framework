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

public class ChangePasswordAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String apiUrl;
    public ServiceAPI serviceAPI;

    public ChangePasswordAPI(ScenarioContext context) throws Exception {
        this.context = context;
        serviceAPI = new ServiceAPI(context);
        apiUrl = getServerUrl() + LoadProperties.getAULoyaltyPropertyValue("changePassword");
        logger.info("Change password API URL :- " + apiUrl);
    }

    /**
     * Method to get the Change Password api request body from json file
     *
     * @return Object
     * @throws Exception
     */
    public Object getChangePasswordRequestBodyFromJson() throws Exception {
        Object changePwdJson;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir + getAULoyaltyPropertyValue("changePasswordJson");
        changePwdJson = jsonFileReader(path);
        System.out.println("Change Password API request body is - " + changePwdJson);
        return changePwdJson;
    }

    /**
     * Method to get Change password header with SiteId and okta
     * @return map<String, String>
     * @throws Exception
     */
    public Map<String, String> getSiteIdOktaHeaderAsMap(String endpoint) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint,"Valid-Userid-Oldpwd-Newpwd");
        String oktaMail = (String) dataMap.get("user_id");
        headerMap.put("siteId", LoadProperties.getAULoyaltyPropertyValue("siteid"));
        headerMap.put("okta-accesstoken", oktaMail);
        System.out.println("Site Id and okta as API request header is - " + headerMap);
        return headerMap;
    }

    /**
     * Method to get the Change Password API response
     *
     * @return response
     */
    public Response getChangePasswordResponse(HashMap<String,Object> testdata,String endpoint) throws Exception {
        HashMap<String, Object> bodyMap = new HashMap<>();
        HashMap<String, String> requestMap = new HashMap<>();
        HashMap<String, Object> responseMap = new HashMap<>();
        String valuefield = "value", pwd=null;
        bodyMap = (HashMap<String, Object>) getChangePasswordRequestBodyFromJson();
        // Looping through the input testdata map from spreadsheet
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String) mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            switch (testField) {
                case "user_id":
                    requestMap = (HashMap<String, String>) bodyMap.get("user_id");
                    if (testVal.equalsIgnoreCase("invalidvalue")) {
                        requestMap.put(valuefield, getAlphaNumericString(10));
                    } else if (testVal.equalsIgnoreCase("blank")) {
                        requestMap.put(valuefield, "");
                    } else {
                        requestMap.put(valuefield, testVal);
                    }
                    break;
                case "oldPassword":
                    requestMap = (HashMap<String, String>) bodyMap.get("oldPassword");
                    if (testVal.equalsIgnoreCase("invalidvalue")) {
                        requestMap.put(valuefield, getAlphaNumericString(2));
                    } else if (testVal.equalsIgnoreCase("blank")) {
                        requestMap.put(valuefield, "");
                    } else if (testVal.equalsIgnoreCase("validvalue")) {
                        requestMap.put(valuefield, getAULoyaltyPropertyValue("newPassword"));
                    }
                    break;
                case "newPassword":
                    requestMap = (HashMap<String, String>) bodyMap.get("newPassword");
                    if (testVal.equalsIgnoreCase("invalidvalue")) {
                        requestMap.put(valuefield, getAlphaNumericString(2));
                    } else if (testVal.equalsIgnoreCase("blank")) {
                        requestMap.put(valuefield, "");
                    } else if (testVal.equalsIgnoreCase("validvalue")) {
                        pwd = getAlphaNumericString(10);
                        requestMap.put(valuefield, pwd);
                    }
                    break;
                }
            System.out.println("Updated change password Request body :" + bodyMap);
            }
            Response response = BaseApi.LoyaltypostAPI(bodyMap, getSiteIdOktaHeaderAsMap(endpoint), apiUrl);
            System.out.println("Change password response is :" + response);
            logger.info("Change password response is :- " + response);
            JsonPath jsonPathEvaluator = (JsonPath) response.jsonPath();
            responseMap = jsonPathEvaluator.get();
            //Checking if change password is successful then storing it for future reference
            if(responseMap.containsKey("provider")){
                updateConfigValue("newPassword", pwd);
            }
            return response;
        }
}
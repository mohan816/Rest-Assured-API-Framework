package com.au.loyalty.api.service;


import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;


import java.util.HashMap;
import java.util.Map;

public class AccountDetailsAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private String apiUrl;
    private RegistrationAPI acctDetails;
    public ServiceAPI serviceAPI;

    public AccountDetailsAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getServerUrl()+ LoadProperties.getAULoyaltyPropertyValue("acctDetails");
        logger.info("Registration API URL :- " + apiUrl);
    }

    /**
     * Method to get Change password header with SiteId and okta
     * @return map<String, String>
     * @throws Exception
     */
    public Map<String, String> getSiteIdOktaHeaderAsMap(HashMap<String, Object> dataMap, String endpoint) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        String oktaMail = (String) dataMap.get("okta-accesstoken");
        headerMap.put("siteId", LoadProperties.getAULoyaltyPropertyValue("siteid"));
        headerMap.put("okta-accesstoken", oktaMail);
        System.out.println("Site Id and okta as API request header is - " + headerMap);
        return headerMap;
    }

    /**
     * Method to get the Account details API response
     * @return response
     */
    public Response getResponseDetails(HashMap<String, Object> dataMap) throws Exception {
        System.out.println("Updated request body :"+dataMap);
        Response response = BaseApi.getAPI(dataMap,apiUrl);
        logger.info("Response details is :- " + response);
        return response;
    }

    /**
     * Method to get Account Details JSON API response
     * @return response
     */
    public Object getAccountDetailRequestBodyFromJson() throws Exception {
        Object accountDetailsJson;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir+getAULoyaltyPropertyValue("accountDetailsJson");
        accountDetailsJson = jsonFileReader(path);
        System.out.println("Account Details API request body is - " + accountDetailsJson);
        return accountDetailsJson;
    }

    /**
     * Method to get the Account details API response
     * @return response
     */
    public Response updateAccountDetailsResponse(HashMap<String, Object> dataMap, String endpoint) throws Exception {
        HashMap<String, Object> bodyMap = new HashMap<>();
        HashMap<String, String> headerMap = new HashMap<>();
        bodyMap = (HashMap<String, Object>) getAccountDetailRequestBodyFromJson();
        // Looping through the testdata map
        for (Map.Entry mapElement : dataMap.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) dataMap.get(testField);
            System.out.println("Profile node : "+bodyMap.containsKey(testField));
            if(testField.equalsIgnoreCase("okta-accesstoken")){
                headerMap.put(testField, testVal);
                System.out.println("Updated request header :" + headerMap);
            }else {
                // Check Input test field is blank or not
                if (!testField.equalsIgnoreCase("blank")) {
                    bodyMap.put(testField, testVal);
                    System.out.println("Updated profile node :" + bodyMap);
                } else {
                    bodyMap.put(testField, "");
                    System.out.println("Updated profile node :" + bodyMap);
                }
            }
        }
        System.out.println("Updated header :"+headerMap);
        System.out.println("Updated request body :"+bodyMap);
        Response response = BaseApi.putAPI(bodyMap,getSiteIdOktaHeaderAsMap(dataMap,endpoint),apiUrl);
        logger.info("Extended search response is :- " + response);
        return response;
    }

}

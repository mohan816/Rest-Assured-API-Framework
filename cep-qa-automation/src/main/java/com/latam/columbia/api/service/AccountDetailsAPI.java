package com.latam.columbia.api.service;


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

    public AccountDetailsAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getlatamServerUrl()+ LoadProperties.readCommonConfigProperty("acctDetails");
        LoadProperties.logger.info("Registration API URL :- " + apiUrl);
    }

    /**
     * Method to get the Account details API response
     * @return response
     */
    public Response getResponseDetails(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        String LoginWith = getLatamColumbiaPropertyValue("LoginWith");
        String Okta = (String) dataMap.get("okta-accesstoken");
        if(Okta==""){
            headerMap.put("okta-accesstoken",Okta);
        }else {
            if(LoginWith.equalsIgnoreCase("accesstoken")){
                headerMap.put("okta-accesstoken",getLatamColumbiaPropertyValue("LatamAccessToken"));
            }else{
                headerMap.put("okta-accesstoken",Okta);
            }
        }
        System.out.println("Updated request body :"+headerMap);
        Response response = BaseApi.getAPIMap(headerMap,apiUrl);
        LoadProperties.logger.info("Response details is :- " + response);
        return response;
    }

    /**
     * Method to get Account Details JSON API response
     * @return response
     */
    public Object getAccountDetailRequestBodyFromJson() throws Exception {
        Object accountDetailsJson;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir+LoadProperties.getLatamColumbiaPropertyValue("accountDetailsJson");
        accountDetailsJson = jsonFileReader(path);
        System.out.println("Account Details API request body is - " + accountDetailsJson);
        return accountDetailsJson;
    }

    /**
     * Method to get the Account details API response
     * @return response
     */
    public Response updateAccountDetailsResponse(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, Object> bodyMap = new HashMap<>();
        HashMap<String, String> headerMap = new HashMap<>();
        String LoginWith = getLatamColumbiaPropertyValue("LoginWith");
        bodyMap = (HashMap<String, Object>) getAccountDetailRequestBodyFromJson();
        // Looping through the testdata map
        for (Map.Entry mapElement : dataMap.entrySet()) {
            String testField = (String)mapElement.getKey();
            String testVal = (String) dataMap.get(testField);
            System.out.println("Profile node : "+bodyMap.containsKey(testField));
            // Header siteId value update
                if(testField.equalsIgnoreCase("siteId")) {
                    headerMap.put(testField, testVal);
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
            // Header accessToken value update
                if(LoginWith.equalsIgnoreCase("accesstoken")){
                if(testField.equalsIgnoreCase("okta-accesstoken")){
                    headerMap.put(testField, getLatamColumbiaPropertyValue("LatamAccessToken"));
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

            }else {
                if(testField.equalsIgnoreCase("okta-accesstoken")){
                    headerMap.put(testField, testVal);
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
        }
        System.out.println("Updated header :"+headerMap);
        System.out.println("Updated request body :"+bodyMap);
        String apiServer = getAppServerForProject(getProject());
        apiUrl = apiServer+ readCommonConfigProperty("acctDetails");
        Response response = BaseApi.putAPI(bodyMap,headerMap,apiUrl);
        LoadProperties.logger.info("Extended search response is :- " + response);
        return response;
    }

}

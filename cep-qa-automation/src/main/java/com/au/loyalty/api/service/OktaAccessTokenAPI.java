package com.au.loyalty.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OktaAccessTokenAPI extends Basecode {
    private ScenarioContext context;
    private String apiUrl;
    private String authorizeUrl;
    public OktaAccessTokenAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getAULoyaltyPropertyValue("oktaiUrl") +
                getAULoyaltyPropertyValue("QA_apiversion")+LoadProperties.getAULoyaltyPropertyValue("autherisation");

        authorizeUrl = getAULoyaltyPropertyValue("oktaiUrl")+getAULoyaltyPropertyValue("authorizeClient")+getAULoyaltyPropertyValue("oktaClientId")
                      +getAULoyaltyPropertyValue("authCodeChallange")+getAULoyaltyPropertyValue("codeChallange")+getAULoyaltyPropertyValue("authRedirectUrl")
                      +getAULoyaltyPropertyValue("redirectUri")+getAULoyaltyPropertyValue("authSessionToken")+getAULoyaltyPropertyValue("SessionToken");
        logger.info("Content API URL :- " + apiUrl);
    }


    public Map<String, String> getOktaAccessTokenMap(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("password", getAULoyaltyPropertyValue("Password"));
        bodyMap.put("username", (String) dataMap.get("okta-accesstoken"));
        System.out.println("Site Id as API request header is - " + bodyMap);
        return bodyMap;
    }
    /**
     * Method to get the  session Token API response
     * @return response
     * @param dataMap
     */
    public Response getsessionToken(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, Object> sessionMap = new HashMap<>();
        String sessionToken = null;
        Response response = BaseApi.postAPI(getOktaAccessTokenMap(dataMap),headerMap,apiUrl);
        logger.info("Response details is :- " + response);
        JsonPath jsonPathEvaluator = (JsonPath) response.jsonPath();
        responseMap = jsonPathEvaluator.get();
        sessionToken = (String) responseMap.get("sessionToken");
        //Checking if sessionToken  updated successful then storing it for future reference
        if(responseMap.containsKey("sessionToken")){
            updateConfigValue("SessionToken", sessionToken);
        }
        return response;
    }
    public Response getAuthorizeCode() throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        Response response = BaseApi.getAPIMap(headerMap,authorizeUrl);
        logger.info("Response details is :- " + response);
        Headers headers = response.getHeaders();
        int rescode = response.getStatusCode();
        Map<String, String> cookies=response.getCookies();
        System.out.println(rescode);
        System.out.println(cookies);
        Header responseUrl = headers.get("location");
        final URI uri = URI.create(String.valueOf(responseUrl));
        final String query = uri.getQuery();
        System.out.println(String.format("EXTRACTED QUERY [%s]", query));
        final String part1 = query.substring(query.indexOf('=')+1, query.indexOf('&'));
        System.out.println(String.format("EXTRACTED PART 1 [%s]", part1));
        return response;

    }

}

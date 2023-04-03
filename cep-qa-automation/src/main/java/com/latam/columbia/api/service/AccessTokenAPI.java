package com.latam.columbia.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class AccessTokenAPI extends Basecode {
    private ScenarioContext context;
    private String apiUrl;
    private String authorizeUrl;
    private String TokenUrl;

    public AccessTokenAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getLatamColumbiaPropertyValue("oktaiUrl") +
                getLatamColumbiaPropertyValue("QA_apiversion") + LoadProperties.getLatamColumbiaPropertyValue("autherisation");

        authorizeUrl = getLatamColumbiaPropertyValue("oktaiUrl") + getLatamColumbiaPropertyValue("authorizeClient") + getLatamColumbiaPropertyValue("oktaClientId")
                + getLatamColumbiaPropertyValue("authCodeChallange") + getLatamColumbiaPropertyValue("codeChallange") + getLatamColumbiaPropertyValue("authRedirectUrl")
                + getLatamColumbiaPropertyValue("redirectUri") + getLatamColumbiaPropertyValue("authSessionToken") + getLatamColumbiaPropertyValue("SessionToken");

        TokenUrl = getAULoyaltyPropertyValue("oktaiUrl") + getAULoyaltyPropertyValue("Token");
        logger.info("Content API URL :- " + apiUrl);
    }


    public Map<String, String> getOktaAccessTokenMap(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, String> bodyMap = new HashMap<>();
//        bodyMap.put("password", getLatamColumbiaPropertyValue("Password"));
        bodyMap.put("password", (String) dataMap.get("Password"));
        bodyMap.put("username", (String) dataMap.get("okta-accesstoken"));
        System.out.println("Site Id as API request header is - " + bodyMap);
        return bodyMap;
    }

    /**
     * Method to get the  session Token API response
     *
     * @param dataMap
     * @return response
     */
    public Response getsessionToken(HashMap<String, Object> dataMap) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, Object> sessionMap = new HashMap<>();
        String sessionToken = null;
        Response response = BaseApi.postAPI(getOktaAccessTokenMap(dataMap), headerMap, apiUrl);
        logger.info("Response details is :- " + response);
        JsonPath jsonPathEvaluator = (JsonPath) response.jsonPath();
        responseMap = jsonPathEvaluator.get();
        sessionToken = (String) responseMap.get("sessionToken");
        //Checking if sessionToken  updated successful then storing it for future reference
        if (responseMap.containsKey("sessionToken")) {
            updateAccessToken("SessionToken", sessionToken);
        }
        return response;
    }

    /**
     * Method to get the  Authorization code
     *
     * @param
     * @return response
     */

    public String getAuthorizeCode() throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        Response response = BaseApi.getMap(headerMap, authorizeUrl);
        logger.info("Response details is :- " + response);
        String authCode = null;
        Headers headers = response.getHeaders();
        Header responseUrl = headers.get("location");
        String url = responseUrl.toString();
        String[] parts = url.split("code=");
        String[] code = parts[1].split("&state");
        System.out.println(code[0]);
        authCode = code[0];
        System.out.println(authCode);
        return authCode;
    }

    /**
     * Method to get the  Access Token API response
     *
     * @param
     * @return response
     */
    public Response getAccessToken() throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> responseMap = new HashMap<>();
        headerMap.put("Content-Type", getLatamColumbiaPropertyValue("Content"));
        headerMap.put("Accept", getLatamColumbiaPropertyValue("Accept"));
        HashMap<String, Object> sessionMap = new HashMap<>();
        String AccessToken = null;
        String oktaClientId = getLatamColumbiaPropertyValue("oktaClientId");
        String redirectUri = getLatamColumbiaPropertyValue("redirectUri");
        String codeVerifier = getLatamColumbiaPropertyValue("codeVerifier");
        String authorizationCode = getAuthorizeCode();
        String token = "client_id=" + oktaClientId + "&redirect_uri=" + redirectUri + "&grant_type=authorization_code&code_verifier=" + codeVerifier + "&code=" + authorizationCode + "";
        Response response = BaseApi.postAPI(token, headerMap, TokenUrl);
        logger.info("Response details is :- " + response);
        JsonPath jsonPathEvaluator = (JsonPath) response.jsonPath();
        responseMap = jsonPathEvaluator.get();
        AccessToken = (String) responseMap.get("access_token");
        //Checking if AccessToken  updated successful then storing it for future reference
        if (responseMap.containsKey("access_token")) {
            updateAccessToken("LatamAccessToken", AccessToken);
        }
        return response;
    }
}


package com.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.testng.TestRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;
import com.utils.LoadProperties;

public class BaseApi extends Basecode{
    public static final Logger logger = Logger.getLogger("devpinoyLogger");
    public LoadProperties loadProperties;
    public BaseApi() throws ConfigurationException {
    }

    /**
     * GET call for provided API endpoint
     *
     * @param headerMap required headers as map
     * @param url url
     * @return response object
     */
    public static Response getAPI(Map<String, Object> headerMap, String url)
            throws Exception{
        RestAssured rest = null;
        Response response =
                rest.given()
                        .contentType(ContentType.JSON)
                        .headers(headerMap)
                        .and()
                        .when()
                        .get(url);
        logger.info("GET call to URL " + url);
        logger.info("GET Response" + response.prettyPrint());
        return response;
    }

    /**
     * GET call for provided API endpoint
     *
     * @param headerMap required headers as map
     * @param url url
     * @return response object
     */
    public static Response getMap(Map<String, String> headerMap, String url)
            throws Exception{
        RestAssured rest = null;
        Response response =
                rest.given()
                        .contentType(ContentType.URLENC)
                        .redirects().follow(false)
                        .expect()
                        .statusCode(302)
                        .when()
                        .get(url);
        logger.info("GET call to URL " + url);
        logger.info("GET Response" + response.prettyPrint());
        return response;
    }
    public static Response getAPIMap(Map<String, String> headerMap, String url)
            throws Exception{
        RestAssured rest = null;
        Response response =
                rest.given()
                        .contentType(ContentType.JSON)
                        .headers(headerMap)
                        .and()
                        .when()
                        .get(url);
        logger.info("GET call to URL " + url);
        logger.info("GET Response" + response.prettyPrint());
        return response;
    }

    public static Response delAPI(Object body ,Map<String, String> headerMap, String url)
            throws Exception{
        RestAssured rest = null;
        Response response =
                rest.given()
                        .contentType(ContentType.JSON)
                        .headers(headerMap)
                        .and()
                        .body(body)
                        .when()
                        .delete(url);
        logger.info("GET call to URL " + url);
        logger.info("GET Response" + response.prettyPrint());
        return response;
    }

    /**
     * POST call for provided API endpoint
     *
     * @param headerMap header as map
     * @param url method resource URI
     * @return Response object
     */
//    public static Response postAPI(Map<String, String> bodyMap, Map<String, String> headerMap,
//                                   String url) throws Exception {
////        String clientid = LoadProperties.getPropertyValue("clientid");
////        String clientsecret = LoadProperties.getPropertyValue("clientsecret");
//
//        RestAssured rest = null;
//        Response response =
//                rest.given()
//                        .contentType("application/x-www-form-urlencoded;charset=utf-8")
//                        //.params()
//                        .headers(headerMap)
////                        .formParam("client_id", clientid)
////                        .formParam("client_secret", clientsecret)
//                        .urlEncodingEnabled(true)
//                        .and()
//                        .body(bodyMap)
//                        .when()
//                        .log()
//                        .all()
//                        .post(url);
//        logger.info("POST call to URL " + url);
//        logger.info("POST Response" + response.prettyPrint());
//        return response;
//    }

    /**
     * POST call for provided API endpoint
     *
     * @param headerMap header as map
     * @param url method resource URI
     * @return Response object
     */
    public static Response postAPI(Object body, Map<String, String> headerMap,
                                   String url) throws Exception {
        headerMap.put("siteId", readCommonConfigProperty( getProject()+"siteid"));
        headerMap.put("x-api-key", readCommonConfigProperty(getProject()+"x-api-key"));
        RestAssured rest = null;
        Response response =
                rest.given()
                        //.contentType("application/x-www-form-urlencoded;charset=utf-8")
                        .contentType("application/json")
                        //.params()
                        .headers(headerMap)
                        //.formParam("client_id", clientid)
                        //.formParam("client_secret", clientsecret)
                        .urlEncodingEnabled(true)
                        .and()
                        .body(body)
                        .when()
                        .log()
                        .all()
                        .post(url);
        logger.info("POST call to URL " + url);
        logger.info("POST Response" + response.prettyPrint());
        return response;
    }
    public static Response LoyaltypostAPI(Object body, Map<String, String> headerMap,
                                          String url) throws Exception {
        headerMap.put("siteId", LoadProperties.getAULoyaltyPropertyValue("siteid"));
        headerMap.put("x-api-key", LoadProperties.getAULoyaltyPropertyValue("x-api-key"));

        RestAssured rest = null;
        Response response =
                rest.given()
                        //.contentType("application/x-www-form-urlencoded;charset=utf-8")
                        .contentType("application/json")
                        //.params()
                        .headers(headerMap)
                        //.formParam("client_id", clientid)
                        //.formParam("client_secret", clientsecret)
                        .urlEncodingEnabled(true)
                        .and()
                        .body(body)
                        .when()
                        .log()
                        .all()
                        .post(url);
        logger.info("POST call to URL " + url);
        logger.info("POST Response" + response.prettyPrint());
        return response;
    }

    /**
     * PUT call for provided API endpoint
     *
     * @param headerMap header as map
     * @param url method resource URI
     * @return Response object
     */
    public static Response putAPI(Object body, Map<String, String> headerMap,
                                  String url) throws Exception {
        RestAssured rest = null;
        Response response =
                rest.given()
                        //.contentType("application/x-www-form-urlencoded;charset=utf-8")
                        .contentType("application/json")
                        //.params()
                        .headers(headerMap)
                        //.formParam("client_id", clientid)
                        //.formParam("client_secret", clientsecret)
                        .urlEncodingEnabled(true)
                        .and()
                        .body(body)
                        .when()
                        .log()
                        .all()
                        .put(url);
        logger.info("PUT call to URL " + url);
        logger.info("PUT Response" + response.prettyPrint());
        return response;
    }

    /**
     * Read JSON data from file as map , Reads data from testData\api folder
     *
     * @param keyValuesToBeReadFromJson
     * @return Map<String, String> of json data
     * @throws Exception
     */
    public static Map<String, String> readJsonTestDataAsMap(String keyValuesToBeReadFromJson)
            throws Exception {
        logger.info("Reading Json data " + keyValuesToBeReadFromJson);
        return getStringStringMap(keyValuesToBeReadFromJson, readAPIJsonTestData());
    }

    public static JsonObject readAPIJsonTestData()
            throws Exception {
        String fileName = "";
        Gson gson = new Gson();
        JsonReader reader = getJsonReader(fileName);
        return gson.fromJson(reader, JsonObject.class);
    }

    /**
     * Read Test data json from TestData folder , Make sure to have test data folder and corresponding
     * API folder
     *
     * @param fileName
     * @return JSONReader
     * @throws Exception
     */
    private static JsonReader getJsonReader(String fileName) throws Exception {
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader("testData/api/" + fileName));
            logger.info("Reading json data from file @ testData/api/" + fileName);
        } catch (FileNotFoundException e) {
            throw new Exception(
                    "Unable to find JSON test data file @ /testData/api/" + fileName, e);
        }
        return reader;
    }

    private static Map<String, String> getStringStringMap(
            String keyValuesToBeReadFromJson, JsonObject jsonObject) {
        Gson gson = new Gson();
        JsonObject object = jsonObject.getAsJsonObject();
        JsonElement element = object.get(keyValuesToBeReadFromJson);
        Type stringStringMap = new TypeToken<Map<String, String>>() {}.getType();
        return gson.fromJson(element, stringStringMap);
    }

}

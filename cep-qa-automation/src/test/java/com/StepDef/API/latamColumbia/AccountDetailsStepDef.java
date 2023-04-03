package com.StepDef.API.latamColumbia;

import com.utils.BaseApi;
import com.latam.columbia.api.service.*;
import com.utils.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountDetailsStepDef extends BaseApi {
    private ScenarioContext context;
    public AccountDetailsAPI acctDetails;
    public HomeAPI homeAPI;
    public ServiceAPI serviceAPI;
    public PromotionsAPI promotionsAPI;
    public GetAPI getAPI;
    public AccessTokenAPI accessTokenAPI;
    private Response response;

    public AccountDetailsStepDef(ScenarioContext context) throws Exception {
        this.context = context;
        acctDetails = new AccountDetailsAPI(context);
        homeAPI = new HomeAPI(context);
        serviceAPI = new ServiceAPI(context);
        promotionsAPI = new PromotionsAPI(context);
        accessTokenAPI = new AccessTokenAPI(context);
        getAPI = new GetAPI(context);
    }

    public void setResponseDetails(Response response) {
        context.previousResponse = response;
        //context.addResponseInTransactionMap("registration",response);
        System.out.println(context.previousResponse.prettyPrint());
    }


    @Given("send get request to latam {string} for {string}")
    public void sendGetRequestToLatamFor(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        //registration = new RegistrationAPI(context);
        dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint, testcase);
        switch (endpoint) {
            case "Home":
                response = homeAPI.getHomeApiDetails();
                break;
            case "Promotion":
                response = promotionsAPI.getPromotionsResponse(dataMap);
                break;
            case "Okta":
                response = accessTokenAPI.getAccessToken();
                break;
            default:
                response = getAPI.getAPIResponseDetails(dataMap, endpoint);
        }
        setResponseDetails(response);
    }

    @Then("verify latam {string} details api response for {string}")
    public void verifyLatamDetailsApiResponseFor(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> contentMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endpoint,testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        // Following conditional is to check response body contains error message or not for negative usecase
        if(actResponseMap.containsKey("msg")) {
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
                System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isEqualTo(testVal);
                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
            }
        }else {
            // Following conditional is to check response body for positive usecase
            HashMap<String, Object> actCustomerMap = new HashMap<>();
            HashMap<String, Object> actCustomerMaparray = new HashMap<>();
            actResponseMap =  context.previousResponse.getBody().jsonPath().get();

            actCustomerMap = (HashMap<String, Object>) actResponseMap.get("customers");
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
                System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actCustomerMap.get(expectedField).toString()).isEqualTo(testVal);
                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actCustomerMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
            }
        }
    }

    @Given("send put request to latam {string} for {string}")
    public void sendPutRequestToLatamFor(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        //registration = new RegistrationAPI(context);
        dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint,testcase);
        response = acctDetails.updateAccountDetailsResponse(dataMap);
        setResponseDetails(response);
    }

    @Then("verify {string} sales target api details response for {string}")
    public void verifySalesTargetApiDetailsResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> valuesMap = new HashMap<>();
        String expectedField = null, testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint, testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        String[] keyValues = {"title", "excerpt", "content",
                "terms", "category"};
        String[] Values = {"title", "description"};
        if (actResponseMap.containsKey("data")) {
            ArrayList<Object> dataArr = (ArrayList<Object>) actResponseMap.get("data");
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
//                int numRecord = dataArr.size();
                int count = (int) actResponseMap.get("totalCount");
                if(count>1)
                    for (int i = 0; i < count; i++) {
                        dataMap = (HashMap<String, Object>) dataArr.get(i);
                        if (expectedField.equalsIgnoreCase("en") || expectedField.equalsIgnoreCase("es")) {
                            if(endPoint.equalsIgnoreCase("Faq")){
                                for (int j = 0; j < Values.length; j++) {
                                    valuesMap = (HashMap<String, Object>) dataMap.get(Values[j]);
                                    System.out.println("ExiectedField :" + expectedField + "   " +  "TestValue :" + testVal);
                                    try {
                                        assertThat(valuesMap.get(expectedField).toString()).isNotNull()
                                                .as(expectedField + " field value is is Null.");
                                    } catch (NullPointerException e) {
                                        assertThat(valuesMap.get(expectedField)).isEqualTo(null)
                                                .as(expectedField + " field value is is Null.");
                                    }
                                }
                            }else{
                                for (int j = 0; j < keyValues.length; j++) {
                                    valuesMap = (HashMap<String, Object>) dataMap.get(keyValues[j]);
                                    System.out.println("ExiectedField :" + expectedField + "   " +  "TestValue :" + testVal);
                                    try {
                                        assertThat(valuesMap.get(expectedField).toString()).isNotNull()
                                                .as(expectedField + " field value is is Null.");
                                    } catch (NullPointerException e) {
                                        assertThat(valuesMap.get(expectedField)).isEqualTo(null)
                                                .as(expectedField + " field value is is Null.");
                                    }
                                }
                            }


                        } else {
                            if (!testVal.equalsIgnoreCase("NotNull")) {
                                System.out.println("ExiectedField :" + expectedField + "   " +  "TestValue :" + testVal);
                                assertThat(dataMap.get(expectedField).toString()).isEqualTo(testVal);
                            } else if (testVal.equalsIgnoreCase("NotNull")) {
                                System.out.println("ExiectedField :" + expectedField + "TestValue :" + testVal);
                                try {
                                    assertThat(dataMap.get(expectedField).toString()).isNotNull()
                                            .as(expectedField + " field value is is Null.");
                                } catch (NullPointerException e) {
                                    assertThat(dataMap.get(expectedField)).isEqualTo(null)
                                            .as(expectedField + " field value is is Null.");
                                }
                            }
                        }

                    }
            }

        }else if(actResponseMap.containsKey("")){

        }
        else {
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
                System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isEqualTo(testVal);
                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
            }
        }
    }
    @Then("verify {string} vendor list api details response for {string}")
    public void verifyVendorListApiDetailsResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> objectMap = new HashMap<>();
        String expectedField = null, testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint, testcase);
        ArrayList<Map<String,?>> jsonAsArrayList =  context.previousResponse.getBody().jsonPath().get();
        objectMap = (HashMap<String, Object>) jsonAsArrayList.get(0);
        for (Map.Entry mapElement : expResponseMap.entrySet()) {
            expectedField = (String) mapElement.getKey();
            testVal = (String) expResponseMap.get(expectedField);
            System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
            if (!testVal.equalsIgnoreCase("NotNull")) {
                assertThat(objectMap.get(expectedField).toString()).isEqualTo(testVal);
            } else if (testVal.equalsIgnoreCase("NotNull")) {
                assertThat(objectMap.get(expectedField).toString()).isNotNull()
                        .as(expectedField + " field value is is Null.");
            }
        }
    }

    @Given("send get request to latam {string} for {string} and store {string}")
    public void sendGetRequestToLatamandStore(String endpoint, String testcase, String field) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        String fieldVal = null;
        //registration = new RegistrationAPI(context);
        dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint, testcase);
        switch (endpoint) {
            case "Home":
                response = homeAPI.getHomeApiDetails();
                break;
            case "Promotion":
                response = promotionsAPI.getPromotionsResponse(dataMap);
                break;
            case "Okta":
                response = accessTokenAPI.getAccessToken();
                break;
            default:
                response = getAPI.getAPIResponseDetails(dataMap, endpoint);
        }
        setResponseDetails(response);
        fieldVal = context.previousResponse.jsonPath().get("metadata.totalItems").toString();
        context.setDataStore("prvTotal",fieldVal);
        //updateConfigValue("orderHistTotal",fieldVal);
    }

    @Given("send get request to latam {string} for {string} and verify {string}")
    public void sendGetRequestToLatamandVerifyField(String endpoint, String testcase, String field) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        String fieldVal = null, fieldConfig = null;
        dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint, testcase);
        // Following wait is required to give time for the order posted before this stepdef
        Thread.sleep(60_000);
        response = getAPI.getAPIResponseDetails(dataMap, endpoint);
        setResponseDetails(response);
        fieldVal = context.previousResponse.jsonPath().get("metadata.totalItems").toString();
        //fieldConfig = getLatamColumbiaPropertyValue("orderHistTotal");
        fieldConfig = context.getDataStore().get("prvTotal").toString();
        assertThat(fieldVal).isNotEqualTo(fieldConfig)
                .as("Previous order count is "+fieldConfig+" current order count is "+fieldVal);
    }

}

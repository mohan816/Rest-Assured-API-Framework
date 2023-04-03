package com.StepDef.API.au.loyalty;

import com.au.loyalty.api.service.*;

import com.utils.BaseApi;
import com.utils.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountDetailsAPIStepDef extends BaseApi {
    private ScenarioContext context;
    public AccountDetailsAPI acctDetails;
    public RegistrationAPI registrationAPI;
    public ServiceAPI serviceAPI;
    public ContentAPI contentAPI;
    public GetRewardsAPI getRewardsAPI;
    public OktaAccessTokenAPI oktaAccessTokenAPI;
    private Response response;

    public AccountDetailsAPIStepDef(ScenarioContext context) throws Exception {
        this.context = context;
        acctDetails = new AccountDetailsAPI(context);
        registrationAPI = new RegistrationAPI(context);
        contentAPI = new ContentAPI(context);
        serviceAPI = new ServiceAPI(context);
        oktaAccessTokenAPI = new OktaAccessTokenAPI(context);
        getRewardsAPI = new GetRewardsAPI(context);
    }

    public void setResponseDetails(Response response) {
        context.previousResponse = response;
        //context.addResponseInTransactionMap("registration",response);
        System.out.println(context.previousResponse.prettyPrint());
    }

    @When("send get request to {string} for {string}")
    public void sendGetRequestDetailsForTestcase(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        //registration = new RegistrationAPI(context);
        dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint, testcase);
        if(endpoint.equalsIgnoreCase("ContactUS")) {
            dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint, testcase);
            response = acctDetails.getResponseDetails(dataMap);
        }if(endpoint.equalsIgnoreCase("GetAccountDetails")) {
            response = acctDetails.getResponseDetails(dataMap);
        }if(endpoint.equalsIgnoreCase("Content")) {
            response = contentAPI.getContentDetails();
        }if(endpoint.equalsIgnoreCase("FAQ")) {
            response = contentAPI.getFaqDetails();
        }if(endpoint.equalsIgnoreCase("GetRewards")) {
            response = getRewardsAPI.getRewardsDetails(dataMap);
        }if(endpoint.equalsIgnoreCase("GetClaimedRewards")) {
            response = getRewardsAPI.getClaimedRewardsDetails(dataMap);
        }if(endpoint.equalsIgnoreCase("GetMyActivity")) {
            response = getRewardsAPI.getMyDetails(dataMap);
        }if(endpoint.equalsIgnoreCase("Okta")) {
            response = oktaAccessTokenAPI.getAuthorizeCode();
        }
        setResponseDetails(response);
    }

    @When("send put request to {string} for {string}")
    public void sendputRequestAccountDetailsForTestcase(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        //registration = new RegistrationAPI(context);
        dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint,testcase);
        response = acctDetails.updateAccountDetailsResponse(dataMap,endpoint);
        setResponseDetails(response);
    }

    @Then("verify account details api response for {string}")
    public void verifyAccountDetailsApiResponse(String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> customerMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile("UpdateAccountDetails",testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        // Following conditional is to check response body contains error message or not for negative usecase
        if(actResponseMap.containsKey("msg")) {
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
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
            JsonPath jsonPathEvaluator = context.previousResponse.jsonPath();
            customerMap = jsonPathEvaluator.get("customers");
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
                System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(customerMap.get(expectedField).toString()).isEqualTo(testVal);
                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(customerMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
            }

        }
    }


    @Then("verify loyalty {string}  details api response for {string}")
    public void verifyLoyaltyDetailsApiResponseFor(String endpoint, String testcase) throws Exception {
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
            HashMap<String, Object> actCustomerMaparray = new HashMap<>();
            actResponseMap =  context.previousResponse.getBody().jsonPath().get();

//            if(endpoint.equalsIgnoreCase("UpdateAccountDetails")){
//                ArrayList<Object> custArr = (ArrayList<Object>) actResponseMap.get("customers");
//                actCustomerMaparray = (HashMap<String, Object>) custArr.get(0);
//                for (Map.Entry mapElement : expResponseMap.entrySet()) {
//                    expectedField = (String) mapElement.getKey();
//                    testVal = (String) expResponseMap.get(expectedField);
//                    System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
//                    if (!testVal.equalsIgnoreCase("NotNull")) {
//                        assertThat(actCustomerMaparray.get(expectedField).toString()).isEqualTo(testVal);
//                    } else if (testVal.equalsIgnoreCase("NotNull")) {
//                        assertThat(actCustomerMaparray.get(expectedField).toString()).isNotNull()
//                                .as(expectedField + " field value is is Null.");
//                    }
//                }
//            }
//            else{
                HashMap<String, Object> actCustomerMap = new HashMap<>();
                actCustomerMap = (HashMap<String, Object>) actResponseMap.get("customers");
                for (Map.Entry mapElement : expResponseMap.entrySet()) {
                    expectedField = (String) mapElement.getKey();
                    testVal = (String) expResponseMap.get(expectedField);
                    System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
                    try{
                        assertThat(actCustomerMap.get(expectedField).toString()).isNotNull()
                                .as(expectedField + " field value is is Null.");
                    }catch (NullPointerException e) {
                        assertThat(actCustomerMap.get(expectedField)).isEqualTo(null)
                                .as(expectedField + " field value is is Null.");
                    }


                }
            }

        }
    }
//}

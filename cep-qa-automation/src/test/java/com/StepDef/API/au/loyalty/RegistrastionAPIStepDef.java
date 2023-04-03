package com.StepDef.API.au.loyalty;

import com.au.loyalty.api.service.*;

import com.utils.BaseApi;
import com.utils.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrastionAPIStepDef extends BaseApi {
    private ScenarioContext context;
    public RegistrationAPI registration;
    public ResendEmailAPI resendEmail;
    public ForgotPasswordAPI forgotPasswordAPI;
    public VerifyRecoveryAPI verifyRecoveryAPI;
    public ResetPasswordAPI resetPasswordAPI;
    public ChangePasswordAPI changePasswordAPI;
    public ContactUSAPI contactUSAPI;
    public ServiceAPI serviceAPI;
    public ClaimRewardAPI claimRewardAPI;
    public OktaAccessTokenAPI oktaAccessTokenAPI;
    private Response response;

    public RegistrastionAPIStepDef(ScenarioContext context) throws Exception {
        this.context = context;
        serviceAPI = new ServiceAPI(context);
        registration = new RegistrationAPI(context);
        resendEmail = new ResendEmailAPI(context);
        forgotPasswordAPI = new ForgotPasswordAPI(context);
        verifyRecoveryAPI = new VerifyRecoveryAPI(context);
        resetPasswordAPI = new ResetPasswordAPI(context);
        changePasswordAPI = new ChangePasswordAPI(context);
        contactUSAPI = new ContactUSAPI(context);
        claimRewardAPI = new ClaimRewardAPI(context);
        oktaAccessTokenAPI = new OktaAccessTokenAPI(context);
    }

    public void setRegistrationResponse(Response response) {
        context.previousResponse = response;
        //context.addResponseInTransactionMap("registration",response);
        System.out.println(context.previousResponse.prettyPrint());
    }

    @When("post {string} request for {string}")
    public void postRegistrationRequestForTestcase(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> resMap = new HashMap<>();
        Object verifyObj;
        registration = new RegistrationAPI(context);
        dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint,testcase);
        if(endpoint.equalsIgnoreCase("Registration")) {
            response = registration.getRegistrationResponse(dataMap);
        }
        if(endpoint.equalsIgnoreCase("ResendEmail") ){
            response = resendEmail.getResendEmailResponse(dataMap);
        }
        if(endpoint.equalsIgnoreCase("ForgotPassword")){
            response = forgotPasswordAPI.getForgotPasswordResponse(dataMap);
            setRegistrationResponse(response);
        }
        if(endpoint.equalsIgnoreCase("VerifyRecovery")){
            JsonPath jsonPathEvaluator = context.previousResponse.jsonPath();
            resMap.put("recoveryToken",jsonPathEvaluator.getString("recoveryToken"));
            resMap.put("code",jsonPathEvaluator.getString("recoveryOtp"));
            response = verifyRecoveryAPI.getVerifyRecoveryResponse(dataMap,resMap);
        }
        if(endpoint.equalsIgnoreCase("ResetPassword")){
            JsonPath jsonPathEvaluator = context.previousResponse.jsonPath();
            resMap.put("stateToken",jsonPathEvaluator.getString("stateToken"));
            response = resetPasswordAPI.getResetPasswordResponse(dataMap,resMap);
        }
        if(endpoint.equalsIgnoreCase("ClaimReward")){
            HashMap<String, Object> actResponseMap = new HashMap<>();
            HashMap<String, Object> itemsMap = new HashMap<>();
            actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
            ArrayList<Object> dataArr = (ArrayList<Object>) actResponseMap.get("data");
            int numcount = dataArr.size();
            if (numcount==0){
                System.out.println("No Rewards found");
                return;
            }
            for(int i=0;i<1 ;i++){
                itemsMap = (HashMap<String, Object>) dataArr.get(i);
                resMap.put("rewardId",itemsMap.get("rewardId"));
                response = claimRewardAPI.getClaimRewardsDetails(dataMap,resMap,testcase);
                setRegistrationResponse(response);
            }

        }
        if(endpoint.equalsIgnoreCase("BurnReward")){
            JsonPath jsonPathEvaluator = context.previousResponse.jsonPath();
            HashMap<String, Object> couponMap = new HashMap<>();
            ArrayList<Object> couponArr = (ArrayList<Object>) jsonPathEvaluator.get("coupons");
            if (couponArr==null){
                System.out.println("No Coupons found");
                return;
            }
            for(int i=0;i<1 ;i++){
                couponMap = (HashMap<String, Object>) couponArr.get(i);
                resMap.put("couponCode",couponMap.get("couponCode"));
                response = claimRewardAPI.getBurnRewardsDetails(dataMap,resMap,testcase);
            }
            setRegistrationResponse(response);
        }
        if(endpoint.equalsIgnoreCase("ChangePassword")){
            response = changePasswordAPI.getChangePasswordResponse(dataMap,endpoint);
        }
        if(endpoint.equalsIgnoreCase("ContactUS")){
            response = contactUSAPI.getContactUSResponse(dataMap);
            setRegistrationResponse(response);
        }
        if(endpoint.equalsIgnoreCase("Okta")){
            response = oktaAccessTokenAPI.getsessionToken(dataMap);
        }
        setRegistrationResponse(response);
    }
    /**
     *  Registration,ResendEmail,ForgotPassword,VerifyRecovery,ResetPassword
     *  ResetPassword,ChangePassword,ClaimReward,BurnRewards,ContactUS Api response validation
     */
    @Then("verify {string} api response output fields for {string}")
    public void verifyRegistrationApiResponse(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, String> providerMap = new HashMap<>();
        HashMap<String, Object> actualResMap = new HashMap<>();
        String expectedField = null,testVal;
        responseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint,testcase);
        JsonPath jsonPathEvaluator = context.previousResponse.jsonPath();
        actualResMap = jsonPathEvaluator.get();
        if(!endPoint.equalsIgnoreCase("ChangePassword")) {
            for (Map.Entry mapElement : responseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) responseMap.get(expectedField);
                System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(jsonPathEvaluator.getString(expectedField).toString()).containsIgnoringCase(testVal);
                } else {
                    assertThat(jsonPathEvaluator.getString(expectedField).toString()).isNotNull();
                }
            }
        }if(endPoint.equalsIgnoreCase("ChangePassword")) {
            if(actualResMap.containsKey("provider")) {
                providerMap = (HashMap<String, String>) jsonPathEvaluator.get("provider");
                for (Map.Entry mapElement : responseMap.entrySet()) {
                    expectedField = (String) mapElement.getKey();
                    testVal = (String) responseMap.get(expectedField);
                    System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
                    if (!testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(providerMap.get(expectedField).toString()).containsIgnoringCase(testVal);
                    } else {
                        assertThat(providerMap.get(expectedField).toString()).isNotNull();
                    }
                }
            }if(!actualResMap.containsKey("provider")) {
                for (Map.Entry mapElement : responseMap.entrySet()) {
                    expectedField = (String) mapElement.getKey();
                    testVal = (String) responseMap.get(expectedField);
                    System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
                    if (!testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(actualResMap.get(expectedField).toString()).containsIgnoringCase(testVal);
                    } else {
                        assertThat(actualResMap.get(expectedField).toString()).isNotNull();
                    }
                }
            }

        }
    }
    /**
     *  ClaimReward,BurnRewards Api response validation
     */
    @Then("verify {string} response output fields for {string}")
    public void verifyResponseOutputFieldsFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, String> providerMap = new HashMap<>();
        HashMap<String, Object> actualResMap = new HashMap<>();
        String expectedField = null,testVal;
        responseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint,testcase);
        JsonPath jsonPathEvaluator = context.previousResponse.jsonPath();
        actualResMap = jsonPathEvaluator.get();
        ArrayList<Object> dataArr = (ArrayList<Object>) actualResMap.get("data");
        if(actualResMap.containsKey("totalCount")){
            String message = (String) actualResMap.get("message");
            System.out.println(message);
            return;
        }
        for (Map.Entry mapElement : responseMap.entrySet()) {
            expectedField = (String) mapElement.getKey();
            testVal = (String) responseMap.get(expectedField);
            System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
            if (!testVal.equalsIgnoreCase("NotNull")) {
                assertThat(actualResMap.get(expectedField).toString()).containsIgnoringCase(testVal);
            } else {
                assertThat(actualResMap.get(expectedField).toString()).isNotNull();
            }
        }

    }

}

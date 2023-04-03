package com.StepDef.API.latamColumbia;

import com.latam.columbia.api.service.*;
import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistartionAPIStepDef extends BaseApi {
    private ScenarioContext context;
    private ResetPasswordAPI resetPasswordAPI;
    public ForgotPasswordAPI forgotPasswordAPI;
    public VerifyRecoveryAPI verifyRecoveryAPI ;
    public RegistrationAPI registrationAPI;
    public ServiceAPI serviceAPI;
    public ChangePasswordApi changePasswordApi;
    public ValidateTaxIdAPI validateTaxIdAPI;
    public ValidateProfileAPI validateProfileAPI;
    public StoreImageAPI storeImageAPI;
    public ClearCartAPI clearCartAPI;
    public  AddCartAPI addCartAPI;
    public OrderCreateAPI orderCreateAPI;
    public DeleteUserAPI deleteUserAPI;
    public GetAPI getAPI;
    public AccessTokenAPI accessTokenAPI;
    private Response response;

    public RegistartionAPIStepDef(ScenarioContext context) throws Exception {
        this.context = context;
        serviceAPI = new ServiceAPI(context);
        forgotPasswordAPI = new ForgotPasswordAPI(context);
        verifyRecoveryAPI = new VerifyRecoveryAPI(context);
        registrationAPI = new RegistrationAPI(context);
        resetPasswordAPI = new ResetPasswordAPI(context);
        validateTaxIdAPI = new ValidateTaxIdAPI(context);
        validateProfileAPI = new ValidateProfileAPI(context);
        changePasswordApi = new ChangePasswordApi(context);
        storeImageAPI = new StoreImageAPI(context);
        clearCartAPI = new ClearCartAPI(context);
        addCartAPI = new AddCartAPI(context);
        deleteUserAPI = new DeleteUserAPI(context);
        getAPI = new GetAPI(context);
        accessTokenAPI = new AccessTokenAPI(context);
        orderCreateAPI = new OrderCreateAPI(context);

    }

    public void setRegistrationResponse(Response response) {
        context.previousResponse = response;
        //context.addResponseInTransactionMap("registration",response);
        System.out.println(context.previousResponse.prettyPrint());
    }


    @Given("post  latam {string} request for {string}")
    public void postLatamRequestFor(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> resMap = new HashMap<>();

        Object verifyObj;
        dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint,testcase);

        if(endpoint.equalsIgnoreCase("Registration")) {
            response = registrationAPI.getRegistrationResponse(dataMap);
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
            setRegistrationResponse(response);
        }
        if(endpoint.equalsIgnoreCase("UpdateCart") || endpoint.equalsIgnoreCase("AddCart")){
            HashMap<String, Object> actResponseMap = new HashMap<>();
            HashMap<String, Object> itemsMap = new HashMap<>();
            actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
            if(!testcase.equalsIgnoreCase("Update-Rewards")){
                ArrayList<Object> itemsArr = (ArrayList<Object>) actResponseMap.get("products");
                int numcount = itemsArr.size();
                for(int i=0;i<1 ;i++){
                    itemsMap = (HashMap<String, Object>) itemsArr.get(i);
                    resMap.put("id",itemsMap.get("id"));
                    response = addCartAPI.getAddCartDetails(dataMap,resMap,endpoint);
                }
            }else {
                ArrayList<Object> itemsArr = (ArrayList<Object>) actResponseMap.get("items");
                int numcount = itemsArr.size();
                for(int i=0;i<1 ;i++){
                    itemsMap = (HashMap<String, Object>) itemsArr.get(i);
                    resMap.put("id",itemsMap.get("id"));
                    response = addCartAPI.getAddCartDetails(dataMap,resMap,endpoint);
                }
            }

            setRegistrationResponse(response);
        }
        if(endpoint.equalsIgnoreCase("AddRewardsToCart")){
            JsonPath jsonPathEvaluator = context.previousResponse.jsonPath();
            HashMap<String, Object> itemsMap = new HashMap<>();
            ArrayList<Object> itemsArr = (ArrayList<Object>) jsonPathEvaluator.get("items");
            for(int i=0;i<1 ;i++){
                itemsMap = (HashMap<String, Object>) itemsArr.get(i);
                resMap.put("id",itemsMap.get("id"));
                response = addCartAPI.getAddCartDetails(dataMap,resMap,endpoint);
            }
            setRegistrationResponse(response);
        }
        if(endpoint.equalsIgnoreCase("OrderCreate")){
            response = orderCreateAPI.getOrderCreateDetails(dataMap);
            setRegistrationResponse(response);
        }
        if(endpoint.equalsIgnoreCase("ChangePassword")){
            response = changePasswordApi.getChangePasswordResponse(dataMap,endpoint);
        }
        if(endpoint.equalsIgnoreCase("ValidateTaxID")){
            response = validateTaxIdAPI.getValidateTaxIDResponse(dataMap);
        }
        if(endpoint.equalsIgnoreCase("ValidateProfile")){
            response = validateProfileAPI.getValidateProfileResponse(dataMap);
        }
        if(endpoint.equalsIgnoreCase("Storeimage")){
            response = storeImageAPI.getStoreImageResponse(dataMap);
        }
        if(endpoint.equalsIgnoreCase("CustomerImage")){
            response = storeImageAPI.getCustomerImageResponse(dataMap);
        }
        if(endpoint.equalsIgnoreCase("DeliveryEstimate")){
            response = getAPI.getAPIResponseDetails(dataMap,endpoint);
        }
        if(endpoint.equalsIgnoreCase("ClearCart")){
            response =clearCartAPI.getClearCartDetails(dataMap);
        }
        if(endpoint.equalsIgnoreCase("GetCart")){
            response = getAPI.getAPIResponseDetails(dataMap,endpoint);
        }
        if(endpoint.equalsIgnoreCase("DeleteUser")){
            response = deleteUserAPI.getDeleteUserResponse(dataMap);
        }
        if(endpoint.equalsIgnoreCase("Okta")){
            response = accessTokenAPI.getsessionToken(dataMap);
        }

        setRegistrationResponse(response);
    }

    @Then("verify latam {string} api response output fields for {string}")
    public void verifyLatamApiResponseOutputFieldsFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> responseMap = new HashMap<>();
        HashMap<String, String> providerMap = new HashMap<>();
        HashMap<String, Object> actualResMap = new HashMap<>();
        String expectedField = null, testVal;
        responseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint, testcase);
        JsonPath jsonPathEvaluator = context.previousResponse.jsonPath();
        actualResMap = jsonPathEvaluator.get();
        if (!endPoint.equalsIgnoreCase("ChangePassword")) {
            for (Map.Entry mapElement : responseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) responseMap.get(expectedField);
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                    assertThat(jsonPathEvaluator.getString(expectedField).toString()).containsIgnoringCase(testVal);
                } else {
                    System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                    assertThat(jsonPathEvaluator.getString(expectedField).toString()).isNotNull();
                }
            }
        }
        if (endPoint.equalsIgnoreCase("ChangePassword")) {
            if (actualResMap.containsKey("provider")) {
                providerMap = (HashMap<String, String>) jsonPathEvaluator.get("provider");
                for (Map.Entry mapElement : responseMap.entrySet()) {
                    expectedField = (String) mapElement.getKey();
                    testVal = (String) responseMap.get(expectedField);
                    System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                    if (!testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(providerMap.get(expectedField).toString()).containsIgnoringCase(testVal);
                    } else {
                        System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                        assertThat(providerMap.get(expectedField).toString()).isNotNull();
                    }
                }
            }
            if (!actualResMap.containsKey("provider")) {
                for (Map.Entry mapElement : responseMap.entrySet()) {
                    expectedField = (String) mapElement.getKey();
                    testVal = (String) responseMap.get(expectedField);
                    if (!testVal.equalsIgnoreCase("NotNull")) {
                        System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                        assertThat(actualResMap.get(expectedField).toString()).containsIgnoringCase(testVal);
                    } else {
                        System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                        assertThat(actualResMap.get(expectedField).toString()).isNotNull();
                    }
                }
            }



        }
    }

    @Then("verify latam {string} addcart api response output fields for {string}")
    public void verifyLatamAddcartApiResponseOutputFieldsFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> itemsMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        String expectedField = null, testVal;
        int Value=0;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint, testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        ArrayList<Object> itemsArr = (ArrayList<Object>) actResponseMap.get("items");
        if(endPoint.equalsIgnoreCase("AddCart")){
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
        }else if(endPoint.equalsIgnoreCase("UpdateCart")){
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    if(testVal.equalsIgnoreCase("value")){
                        itemsMap = (HashMap<String, Object>) itemsArr.get(0);
                        String updatedValue = LoadProperties.getLatamColumbiaPropertyValue("quantity");
                        String responseValue = (String) itemsMap.get("count");
                        System.out.println("updatedvalue :" + updatedValue + "   " + "responseValue :" + responseValue);
                        assertThat(updatedValue.equalsIgnoreCase(responseValue));
                    }else{
                        System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                        assertThat(actResponseMap.get(expectedField).toString()).isEqualTo(testVal);
                    }
                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                    assertThat(actResponseMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
            }
        }else{
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    if(testVal.equalsIgnoreCase("value")){
                        System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                        for(int i=0;i<itemsArr.size();i++){
                            itemsMap = (HashMap<String, Object>) itemsArr.get(i);
                            assertThat(itemsMap.get(expectedField).toString()).isNotNull();
                        }
                    }else{
                        System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                        assertThat(actResponseMap.get(expectedField).toString()).isEqualTo(testVal);
                    }
                } else {
                    System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                    assertThat(actResponseMap.get(expectedField).toString()).isNotNull();
                }
            }
        }
    }

    @Then("verify latam {string}  image api response output fields for {string}")
    public void verifyLatamImageApiResponseOutputFieldsFor(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> imageMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endpoint,testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        for (Map.Entry mapElement : expResponseMap.entrySet()) {
            expectedField = (String) mapElement.getKey();
            testVal = (String) expResponseMap.get(expectedField);
            System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
            if (!testVal.equalsIgnoreCase("NotNull")) {
                assertThat(actResponseMap.get(expectedField).toString()).isEqualTo(testVal);
            } else if (testVal.equalsIgnoreCase("NotNull")) {
                imageMap = (HashMap<String, Object>) actResponseMap.get("success");
                assertThat(imageMap.get(expectedField).toString()).isNotNull()
                        .as(expectedField + " field value is is Null.");
            }
        }
    }

}

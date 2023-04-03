package com.StepDef.API.latamWhatsApp;

import com.latam.columbia.api.service.*;
import com.utils.ScenarioContext;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;

import java.util.HashMap;

public class AccountStepdef {
    private ScenarioContext context;
    public AccountDetailsAPI acctDetails;
    public HomeAPI homeAPI;
    public ServiceAPI serviceAPI;
    public PromotionsAPI promotionsAPI;
    public GetAPI getAPI;
    public AccessTokenAPI accessTokenAPI;
    private Response response;

    public AccountStepdef(ScenarioContext context) throws Exception {
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

    @Given("send get request to latam WhatsApp {string} for {string}")
    public void sendGetRequestToLatamWhatsAppFor(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap = serviceAPI.getRequestHeaderBodyTestcaseData(endpoint, testcase);
        if (endpoint.equalsIgnoreCase("GetAccountDetails")) {
            response = acctDetails.getResponseDetails(dataMap);
        }
    }
}

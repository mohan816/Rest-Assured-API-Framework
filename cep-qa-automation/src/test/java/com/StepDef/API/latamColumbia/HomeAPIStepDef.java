package com.StepDef.API.latamColumbia;

import com.latam.columbia.api.service.ServiceAPI;
import com.utils.BaseApi;
//import com.au.utils.ScenarioContext;
import com.utils.ScenarioContext;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeAPIStepDef extends BaseApi {
    private ScenarioContext context;
    public ServiceAPI serviceAPI;
    private Response response;

    public HomeAPIStepDef(ScenarioContext context) throws Exception {
        this.context = context;
        serviceAPI = new ServiceAPI(context);
    }


    public void setResponseDetails(Response response) {
        context.previousResponse = response;
        System.out.println(context.previousResponse.prettyPrint());
    }

    @Then("verify {string} page details api response for {string}")
    public void verifyPageDetailsApiResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> contentMap = new HashMap<>();
        HashMap<String, Object> promationsMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint,testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        ArrayList<Object> carouselArr = (ArrayList<Object>) actResponseMap.get("carousel");
        ArrayList<Object> promotionsArr = (ArrayList<Object>) actResponseMap.get("promotions");
        int numCount = carouselArr.size();
        for(int i=0;i<numCount;i++) {
            contentMap = (HashMap<String, Object>) carouselArr.get(i);
            promationsMap = (HashMap<String, Object>) promotionsArr.get(i);
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                System.out.println(expectedField);
                testVal = (String) expResponseMap.get(expectedField);
                if(expectedField.contains("title")){
                    System.out.println("ExiectedField :" +expectedField+ "TestValue :" +testVal );
                    if (!testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(promationsMap.get(expectedField).toString()).isEqualTo(testVal);
                    } else if (testVal.equalsIgnoreCase("NotNull")) {
                        System.out.println(promationsMap.get(expectedField).toString());
                        assertThat(promationsMap.get(expectedField).toString()).isNotNull()
                                .as(expectedField + " field value is is Null.");
                    }
                }
                else{
                    if (testVal.equalsIgnoreCase("valueOrNull")) {
                        System.out.println("ExiectedField :" +expectedField+ "TestValue :" +testVal );
                        try{
                            assertThat(contentMap.get(expectedField).toString()).isNotNull()
                                    .as(expectedField + " field value is is Null.");
                        }catch (NullPointerException e) {
                            assertThat(contentMap.get(expectedField)).isEqualTo(null)
                                    .as(expectedField + " field value is is Null.");
                        }
                    } else if (testVal.equalsIgnoreCase("NotNull")) {
                        System.out.println("ExiectedField :" +expectedField+ "TestValue :" +testVal );
                        System.out.println(contentMap.get(expectedField).toString());
                        assertThat(contentMap.get(expectedField).toString()).isNotNull()
                                .as(expectedField + " field value is is Null.");
                    }
                }

            }
        }
    }
}

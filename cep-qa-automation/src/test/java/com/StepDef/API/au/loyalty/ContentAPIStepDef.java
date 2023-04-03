package com.StepDef.API.au.loyalty;

import com.au.loyalty.api.service.ContentAPI;
import com.au.loyalty.api.service.ServiceAPI;
import com.utils.BaseApi;
import com.utils.ScenarioContext;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentAPIStepDef extends BaseApi {
    private ScenarioContext context;
    public ContentAPI contentAPI;
    public ServiceAPI serviceAPI;
    private Response response;

    public ContentAPIStepDef(ScenarioContext context) throws Exception {
        this.context = context;
        contentAPI = new ContentAPI(context);
        serviceAPI = new ServiceAPI(context);
    }

    public void setResponseDetails(Response response) {
        context.previousResponse = response;
        System.out.println(context.previousResponse.prettyPrint());
    }

    //images and video key value pair in content API response need to check with dev team for clarification
//     images and video response Array is getting null sometimes
    @Then("verify {string} details api response for {string}")
    public void verifyDetailsApiResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> contentMap = new HashMap<>();
        HashMap<String, Object> imageMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint,testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        ArrayList<Object> contentArr = (ArrayList<Object>) actResponseMap.get("data");
        // Following conditional is to check response body contains error message or not for negative usecase
        int numRecord = (int) actResponseMap.get("totalCount");
        for(int i=0;i<numRecord;i++) {
            contentMap = (HashMap<String, Object>) contentArr.get(i);
            ArrayList<Object> imgArr = (ArrayList<Object>) contentMap.get("images");
            ArrayList<Object> videoArr = (ArrayList<Object>) contentMap.get("videos");
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                String testField = (String)mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
                System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
                if (!testVal.equalsIgnoreCase("NotNull") && !testVal.equalsIgnoreCase("Number") && testVal.equalsIgnoreCase("value")) {
                    if(testField.equalsIgnoreCase("image")){
                        imageMap = (HashMap<String, Object>) imgArr.get(0);
                        assertThat(imageMap.get(expectedField).toString()).isNotNull()
                                .as(expectedField + " field value is is Null.");
                    }
//                    else if(testField.equalsIgnoreCase("video")){
//                        imageMap = (HashMap<String, Object>) videoArr.get(0);
//                        assertThat(imageMap.get(expectedField).toString()).isNotNull()
//                                .as(expectedField + " field value is is Null.");
//                    }
                    else{
                        assertThat(contentMap.get(expectedField).toString()).isEqualTo(testVal);
                    }

                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(contentMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
                else if (testVal.equalsIgnoreCase("Number")){
                    assertThat(Integer.parseInt(contentMap.get(expectedField).toString()))
                            .isGreaterThanOrEqualTo(0)
                            .as(expectedField + " field value is num.");
                }
            }
        }
    }

    @Then("verify {string} Rewards details api response for {string}")
    public void verifyRewardsDetailsApiResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> productMap = new HashMap<>();
        HashMap<String, Object> valuesMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint,testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        ArrayList<Object> rewardsArr = (ArrayList<Object>) actResponseMap.get("data");
        String[] keyValues = {"title", "content", "terms",
                "etwCheck", "etwFormMessage", "etwFormErrorMessage", "etwSuccessAlert","etwAlreadyEntered",
                "disclaimer","buttonText","categoryPrimaryText","categorySecondaryText"};
        // Following conditional is to check response body contains error message or not for negative usecase
        for (Map.Entry mapElement : expResponseMap.entrySet()) {
            expectedField = (String) mapElement.getKey();
            testVal = (String) expResponseMap.get(expectedField);
            if(testcase.equalsIgnoreCase("Valid-Content")){
                for (int i = 0; i < rewardsArr.size(); i++) {
                    productMap = (HashMap<String, Object>) rewardsArr.get(i);
                    if (expectedField.equalsIgnoreCase("en") || expectedField.equalsIgnoreCase("es")) {
                        for (int j = 0; j < keyValues.length; j++) {
                            valuesMap = (HashMap<String, Object>) productMap.get(keyValues[j]);
                            System.out.println("ExiectedField :" + expectedField   + "   " +"TestValue :" + testVal);
                            try {
                                assertThat(valuesMap.get(expectedField).toString()).isNotNull()
                                        .as(expectedField + " field value is is Null.");
                            } catch (NullPointerException e) {
                                assertThat(valuesMap.get(expectedField)).isEqualTo(null)
                                        .as(expectedField + " field value is is Null.");
                            }
                        }

                    } else {
                        if (!testVal.equalsIgnoreCase("NotNull")) {
                            assertThat(productMap.get(expectedField).toString()).isEqualTo(testVal);
                        } else if (testVal.equalsIgnoreCase("NotNull")) {
                            System.out.println("ExiectedField :" + expectedField   + "   " +"TestValue :" + testVal);
                            try {
                                assertThat(valuesMap.get(expectedField).toString()).isNotNull()
                                        .as(expectedField + " field value is is Null.");
                            } catch (NullPointerException e) {
                                assertThat(valuesMap.get(expectedField)).isEqualTo(null)
                                        .as(expectedField + " field value is is Null.");
                            }
                        }
                    }

                }
            }else{
                System.out.println("ExiectedField :" + expectedField + "    " +  "TestValue :" + testVal);
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isEqualTo(testVal);
                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
            }

        }
    }

    @Then("verify {string} Activity details api response for {string}")
    public void verifyActivityDetailsApiResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> currentMonthMap = new HashMap<>();
        HashMap<String, Object> valuesMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint,testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        ArrayList<Object> currentMonthArr = (ArrayList<Object>) actResponseMap.get("CurrentMonth");
        // Following conditional is to check response body contains error message or not for negative usecase
        for (Map.Entry mapElement : expResponseMap.entrySet()) {
            expectedField = (String) mapElement.getKey();
            testVal = (String) expResponseMap.get(expectedField);
            if(testcase.equalsIgnoreCase("Valid-Content")){
                for(int i=0;i<currentMonthArr.size();i++){
                    currentMonthMap = (HashMap<String, Object>) currentMonthArr.get(i);
                    if (!testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(currentMonthMap.get(expectedField).toString()).isEqualTo(testVal);
                    } else if (testVal.equalsIgnoreCase("NotNull")) {
                        System.out.println("ExiectedField :" + expectedField   + "   " +"TestValue :" + testVal);
                        try {
                            assertThat(currentMonthMap.get(expectedField).toString()).isNotNull()
                                    .as(expectedField + " field value is is Null.");
                        } catch (NullPointerException e) {
                            assertThat(currentMonthMap.get(expectedField)).isEqualTo(null)
                                    .as(expectedField + " field value is is Null.");
                        }

                    }
                }
            }else{
                System.out.println("ExiectedField :" + expectedField + "    " +  "TestValue :" + testVal);
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isEqualTo(testVal);
                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
            }

        }

    }

    @Then("verify {string} faq details api response for {string}")
    public void verifyFaqDetailsApiResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        HashMap<String, Object> faqMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        HashMap<String, Object> valuesrMap = new HashMap<>();
        HashMap<String, Object> sectionMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint,testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        ArrayList<Object> dataArr = (ArrayList<Object>) actResponseMap.get("data");
        String[] keyValues = {"title", "description", "question",
                "answer"};
        // Following conditional is to check response body contains error message or not for negative usecase
        for (Map.Entry mapElement : expResponseMap.entrySet()) {
            expectedField = (String) mapElement.getKey();
            testVal = (String) expResponseMap.get(expectedField);
            String testField = (String)mapElement.getKey();
            dataMap = (HashMap<String, Object>) dataArr.get(0);
            if(testField.equalsIgnoreCase("en")){
                for (int j = 0; j < keyValues.length; j++) {
                    String key =  keyValues[j];
                    if(key.equalsIgnoreCase("title") || key.equalsIgnoreCase("description")){
                        bodyMap = (HashMap<String, Object>) dataMap.get(keyValues[j]);
                        System.out.println("ExiectedField :" + expectedField   + "   " +"TestValue :" + testVal);
                        assertThat(bodyMap.get(expectedField).toString()).isNotNull()
                                .as(expectedField + " field value is is Null.");
                    }else{
                        ArrayList<Object> sectArr = (ArrayList<Object>) dataMap.get("section");
                        for(int k=0; k<sectArr.size();k++){
                            sectionMap = (HashMap<String, Object>) sectArr.get(k);
                            ArrayList<Object> faqArr = (ArrayList<Object>) sectionMap.get("faqs");
                            for(int i=0; i<faqArr.size();i++){
                                faqMap = (HashMap<String, Object>) faqArr.get(i);
                                valuesrMap = (HashMap<String, Object>) faqMap.get(keyValues[j]);
                                System.out.println("ExiectedField :" + expectedField   + "   " +"TestValue :" + testVal);
                                assertThat(valuesrMap.get(expectedField).toString()).isNotNull()
                                        .as(expectedField + " field value is is Null.");
                            }
                        }
                    }

                }

            }else{
                System.out.println("ExiectedField :" + expectedField + "    " +  "TestValue :" + testVal);
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isEqualTo(testVal);
                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(dataMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
            }

        }
    }
}

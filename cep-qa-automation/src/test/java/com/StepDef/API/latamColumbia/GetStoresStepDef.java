package com.StepDef.API.latamColumbia;


import com.latam.columbia.api.service.ServiceAPI;
import com.utils.Basecode;
import com.utils.ScenarioContext;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetStoresStepDef extends Basecode {
    private ScenarioContext context;
    public ServiceAPI serviceAPI;
    private Response response;

    public GetStoresStepDef(ScenarioContext context) throws Exception {
        this.context = context;
        serviceAPI = new ServiceAPI(context);
    }

    public void setResponseDetails(Response response) {
        context.previousResponse = response;
        System.out.println(context.previousResponse.prettyPrint());
    }

    @Then("verify {string}  api response for {string}")
    public void verifyApiResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> contentMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint,testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        if(actResponseMap.containsKey("data")) {
            ArrayList<Object> contentArr = (ArrayList<Object>) actResponseMap.get("data");
            // Following conditional is to check response body contains error message or not for negative usecase
            int numRecord = contentArr.size();
            for(int i=0;i<numRecord;i++) {
                contentMap = (HashMap<String, Object>) contentArr.get(i);
                for (Map.Entry mapElement : expResponseMap.entrySet()) {
                    expectedField = (String) mapElement.getKey();
                    testVal = (String) expResponseMap.get(expectedField);
                    System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
                    if (!testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(contentMap.get(expectedField).toString()).isEqualTo(testVal);
                    } else if (testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(contentMap.get(expectedField).toString()).isNotNull()
                                .as(expectedField + " field value is is Null.");
                    }
                }
            }

        }else{
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
        }

    }

    @Then("verify {string}  api details response for {string}")
    public void verifyApiDetailsResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> contentMap = new HashMap<>();
        HashMap<String, Object> ordersMap = new HashMap<>();
        HashMap<String, Object> promptionsMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint,testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        ArrayList<Object> ordersArr = (ArrayList<Object>) actResponseMap.get("orders");

        if (actResponseMap.containsKey("metadata")) {
            HashMap<String, Object> metaResMap = (HashMap<String, Object>) actResponseMap.get("metadata");
            for (Map.Entry metaEle : metaResMap.entrySet()) {
                expectedField = (String) metaEle.getKey();
                if(!expectedField.equalsIgnoreCase("totalItems")) {
                    assertThat(metaResMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }else{
                    assertThat(metaResMap.get(expectedField))
                            .as(expectedField + " field value is is Null.");
                }
            }
        }

        if(actResponseMap.containsKey("orders")) {
            int numRecord = ordersArr.size();
            for (int i = 0; i < numRecord; i++) {
                ordersMap = (HashMap<String, Object>) ordersArr.get(i);
                for (Map.Entry mapElement : expResponseMap.entrySet()) {
                    expectedField = (String) mapElement.getKey();
                    testVal = (String) expResponseMap.get(expectedField);
                    System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                    if (!testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(ordersMap.get(expectedField).toString()).isEqualTo(testVal);

                    } else if (testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(ordersMap.get(expectedField).toString()).isNotNull()
                                .as(expectedField + " field value is is Null.");
                    }
                }
            }
        }else if(actResponseMap.containsKey("promotions")){
            ArrayList<Object> promotionsArr = (ArrayList<Object>) actResponseMap.get("promotions");
            int numRecord = promotionsArr.size();
            for (int i = 0; i < numRecord; i++) {
                promptionsMap = (HashMap<String, Object>) promotionsArr.get(i);
                for (Map.Entry mapElement : expResponseMap.entrySet()) {
                    expectedField = (String) mapElement.getKey();
                    testVal = (String) expResponseMap.get(expectedField);
                    System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + testVal);
                    if (!testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(promptionsMap.get(expectedField).toString()).isEqualTo(testVal);
                    } else if (testVal.equalsIgnoreCase("NotNull")) {
                        assertThat(promptionsMap.get(expectedField).toString()).isNotNull()
                                .as(expectedField + " field value is is Null.");
                    }
                }
            }
        }else if(endPoint.equalsIgnoreCase("Holidays")){
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                if(expectedField.equalsIgnoreCase("yearlyHoliday")){
                    ArrayList<Object> yearlyHolidayArr = (ArrayList<Object>) actResponseMap.get("yearlyHoliday");
                    for(int j=0;j<yearlyHolidayArr.size();j++){
                        String value = (String) yearlyHolidayArr.get(j);
                        assertThat(value).isNotNull();
                        System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + value);
                    }
                }else{
                    ArrayList<Object> weeklyHolidayArr = (ArrayList<Object>) actResponseMap.get("weeklyHoliday");
                    for(int j=0;j<weeklyHolidayArr.size();j++){
                        String value = (String) weeklyHolidayArr.get(j);
                        assertThat(value).isNotNull();
                        System.out.println("ExiectedField :" + expectedField + "   " + "TestValue :" + value);
                    }
                }
            }
        }
        else{
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
    @Then("verify {string} rewards api response for {string}")
    public void verifyRewardsaPiResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> detailMap = new HashMap<>();
        HashMap<String, Object> valuesMap = new HashMap<>();
        HashMap<String, Object> rewardsMap = new HashMap<>();
        String expectedField = null, testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint, testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        String[] keyValues = {"mainCategory", "subCategory", "title",
                "shortDescription", "longDescription","size","dimensions","weight","color","package"};
        ArrayList<Object> rewardsArr = (ArrayList<Object>) actResponseMap.get("items");
        if (actResponseMap.containsKey("items")) {
            int numRecord = rewardsArr.size();
            for (int i = 0; i < numRecord; i++) {
                rewardsMap = (HashMap<String, Object>) rewardsArr.get(i);
                detailMap = (HashMap<String, Object>) rewardsMap.get("detail");
                for (Map.Entry mapElement : expResponseMap.entrySet()) {
                    expectedField = (String) mapElement.getKey();
                    testVal = (String) expResponseMap.get(expectedField);
                    if (expectedField.equalsIgnoreCase("en") || expectedField.equalsIgnoreCase("es")) {
                        for (int j = 0; j < keyValues.length; j++) {
                            if(rewardsMap.containsKey(keyValues[j])){
                                valuesMap = (HashMap<String, Object>) rewardsMap.get(keyValues[j]);
                                System.out.println("ExiectedField :" + expectedField + "TestValue :" + testVal);
                                try {
                                    assertThat(valuesMap.get(expectedField).toString()).isNotNull()
                                            .as(expectedField + " field value is is Null.");
                                } catch (NullPointerException e) {
                                    assertThat(valuesMap.get(expectedField)).isEqualTo(null)
                                            .as(expectedField + " field value is is Null.");
                                }
                            }else{
                                valuesMap = (HashMap<String, Object>) detailMap.get(keyValues[j]);
                                System.out.println("ExiectedField :" + expectedField + "TestValue :" + testVal);
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
                    else {
                        try {
                            System.out.println("ExiectedField :" + expectedField + "TestValue :" + testVal);
                            assertThat(rewardsMap.get(expectedField).toString()).isNotNull()
                                    .as(expectedField + " field value is is Null.");
                        } catch (NullPointerException e) {
                            System.out.println("ExiectedField :" + expectedField + "TestValue :" + testVal);
                            assertThat(rewardsMap.get(expectedField)).isEqualTo(null)
                                    .as(expectedField + " field value is is Null.");
                        }
                    }

                }
            }

        }
        else{
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

    @Then("verify {string} Translation api response for {string}")
    public void verifyTranslationApiResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> valuesMap = new HashMap<>();
        String expectedField = null,testVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint,testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        String [] keyvalues = actResponseMap.keySet().toArray(new String[0]);
        int numCount = actResponseMap.size();
        for(int i=0; i<numCount; i++) {
            System.out.println(keyvalues[i]);
            valuesMap = (HashMap<String, Object>) actResponseMap.get(keyvalues[i]);
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
                System.out.println("ExiectedField :" +expectedField+ "   "+"TestValue :" +testVal );
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(valuesMap.get(expectedField).toString()).isEqualTo(testVal);
                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(valuesMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
            }
        }

    }

}

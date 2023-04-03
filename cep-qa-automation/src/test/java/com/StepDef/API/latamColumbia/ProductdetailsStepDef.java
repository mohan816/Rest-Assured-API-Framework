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

public class ProductdetailsStepDef extends Basecode {
    private ScenarioContext context;
    public ServiceAPI serviceAPI;
    private Response response;

    public ProductdetailsStepDef(ScenarioContext context) throws Exception {
        this.context = context;
        serviceAPI = new ServiceAPI(context);
    }

    public void setResponseDetails(Response response) {
        context.previousResponse = response;
        System.out.println(context.previousResponse.prettyPrint());
    }

    @Then("verify {string} details  api response for {string}")
    public void verifyDetailsApiResponseFor(String endPoint, String testcase) throws Exception {
        HashMap<String, Object> expResponseMap = new HashMap<>();
        HashMap<String, Object> actResponseMap = new HashMap<>();
        HashMap<String, Object> productMap = new HashMap<>();
        HashMap<String, Object> valuesMap = new HashMap<>();
        String expectedField = null, testVal, metaVal;
        expResponseMap = serviceAPI.getResponseDataFromTestDataFile(endPoint, testcase);
        actResponseMap = (HashMap<String, Object>) context.previousResponse.getBody().jsonPath().get();
        String[] keyValues = {"displayName", "shortDescription", "longDescription",
                "subBrand", "category", "subCategory", "brand"};
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
        if (actResponseMap.containsKey("products") || actResponseMap.containsKey("sku")) {
            ArrayList<Object> productArr = null;
            if(actResponseMap.containsKey("products")){
                productArr = (ArrayList<Object>) actResponseMap.get("products");
            }else if (actResponseMap.containsKey("sku")) {
                productArr = (ArrayList<Object>) actResponseMap.get("sku");
            }
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
                int numRecord = productArr.size();
                for (int i = 0; i < numRecord; i++) {
                    productMap = (HashMap<String, Object>) productArr.get(i);
                    if (expectedField.equalsIgnoreCase("en") || expectedField.equalsIgnoreCase("es")) {
                        for (int j = 0; j < keyValues.length; j++) {
                            valuesMap = (HashMap<String, Object>) productMap.get(keyValues[j]);
                            System.out.println("ExiectedField :" + expectedField + "   " +  "TestValue :" + testVal);
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
                            System.out.println("ExpectedField :" + expectedField + "   " +  "TestValue :" + testVal);
                            assertThat(productMap.get(expectedField).toString()).isEqualTo(testVal);
                        } else if (testVal.equalsIgnoreCase("NotNull")) {
                            System.out.println("ExpectedField :" + expectedField + "TestValue :" + testVal);
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
            }

        }else{
            for (Map.Entry mapElement : expResponseMap.entrySet()) {
                expectedField = (String) mapElement.getKey();
                testVal = (String) expResponseMap.get(expectedField);
                System.out.println("ExpectedField :" + expectedField + "   " + "TestValue :" + testVal);
                if (!testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isEqualTo(testVal);
                } else if (testVal.equalsIgnoreCase("NotNull")) {
                    assertThat(actResponseMap.get(expectedField).toString()).isNotNull()
                            .as(expectedField + " field value is is Null.");
                }
            }
        }
    }
}


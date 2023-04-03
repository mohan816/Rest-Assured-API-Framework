package com.latam.columbia.api.service;

import com.utils.BaseApi;
import com.utils.Basecode;
import com.utils.LoadProperties;
import com.utils.ScenarioContext;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValidateProfileAPI extends Basecode {
    private ScenarioContext context;
    private String apiUrl;

    public ValidateProfileAPI(ScenarioContext context) throws Exception {
        this.context = context;
        apiUrl = getlatamServerUrl()+ readCommonConfigProperty("validateProfile");
        logger.info("Registration API URL :- " + apiUrl);
    }

    /**
     * Method to get Validate Profile api request body from json file
     * @return Object
     * @throws Exception
     */
    public Object getValidateProfileBodyFromJson() throws Exception {
        Object validateTaxIDJson;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir+ getLatamColumbiaPropertyValue("validateProfileJson");
        validateTaxIDJson = jsonFileReader(path);
        System.out.println("validateTaxID API request body is - " + validateTaxIDJson);
        return validateTaxIDJson;
    }

    /**
     * Method to get the Validate Profile API response
     * @return response
     */
    public Response getValidateProfileResponse(HashMap<String, Object> testdata) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap = (HashMap<String, Object>) getValidateProfileBodyFromJson();
        // Looping through the testdata map
        for (Map.Entry mapElement : testdata.entrySet()) {
            String testField = (String) mapElement.getKey();
            String testVal = (String) testdata.get(testField);
            switch (testField) {
                case "userIdType":
                     if (testVal.equalsIgnoreCase("blank")) {
                        bodyMap.put(testField, "");
                    } else {
                        bodyMap.put(testField, testVal);
                    }
                    break;
                case "userId":
                     if (testVal.equalsIgnoreCase("blank")) {
                        bodyMap.put(testField, "");
                    } else {
                        bodyMap.put(testField, testVal);
                    }
                    break;
                case "erpID":
                    if (testVal.equalsIgnoreCase("blank")) {
                        bodyMap.put(testField, "");
                    } else  {
                        bodyMap.put(testField, testVal);
                    }
                    break;
            }
            System.out.println("Updated change password Request body :" + bodyMap);
        }
        System.out.println("Updated request body :"+bodyMap);
        String apiServer = getAppServerForProject(getProject());
        apiUrl = apiServer+ readCommonConfigProperty("validateProfile");
        Response response = BaseApi.postAPI(bodyMap,headerMap,apiUrl);
        logger.info("Extended search response is :- " + response);
        return response;
    }
}

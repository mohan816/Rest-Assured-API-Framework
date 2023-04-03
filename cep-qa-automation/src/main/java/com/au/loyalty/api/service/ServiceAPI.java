package com.au.loyalty.api.service;

import com.utils.Basecode;
import com.utils.ScenarioContext;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;

public class ServiceAPI extends Basecode {
    private ScenarioContext context;
    private Sheet sheet;
    private XSSFWorkbook wb;

    public ServiceAPI(ScenarioContext context) throws Exception {
        this.context = context;
    }

    /**
     * Method to get the Registration API request test case data
     * @return response
     */
    public HashMap<String, Object> getRequestHeaderBodyTestcaseData(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        String testId,fieldNames = null,values = null;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir+getAULoyaltyPropertyValue("testDataPath");
        sheet = getSheetObject(path,endpoint);
        int rowCount = getExcelRowCount(path,endpoint);
        int colCount = getExcelColumnCount(path,endpoint);
        System.out.println(rowCount);
        System.out.println(colCount);
        for(int i=2;i<rowCount;i++){
            //Excel column initial value
            int j = 1;
            testId = sheet.getRow(i).getCell(j).toString();
            if(!((sheet.getRow(i).getCell(j+1).toString()) ==null)) {
                fieldNames = sheet.getRow(i).getCell(j + 1).toString();
            }
            if(!((sheet.getRow(i).getCell(j + 2).toString()) ==null)) {
                values = sheet.getRow(i).getCell(j + 2).toString();
            }
            System.out.println(testId);
            if(testId.equalsIgnoreCase(testcase)){
                if(!fieldNames.contains(";")){
                    if(values.equalsIgnoreCase("blank")) {
                        dataMap.put(fieldNames,"");
                    }else{
                        dataMap.put(fieldNames,values);
                    }
                }else{
                    // Following code will be executed incase of more than one field value is passed for testcase
                    String[] fieldArr = fieldNames.split(";");
                    String[] valuesArr = values.split(";");
                    for (int r = 0; r < fieldArr.length; r++) {
                        if(valuesArr[r].toString().equalsIgnoreCase("blank")){
                            dataMap.put(fieldArr[r].toString(),"");
                        }else {
                            dataMap.put(fieldArr[r].toString(), valuesArr[r].toString());
                        }
                    }
                }
            }
        }
        System.out.println("Updated request body :"+dataMap);
        return dataMap;
    }

    /**
     * Method to get the Registration API response data
     * @return response
     */
    public HashMap<String, Object> getResponseDataFromTestDataFile(String endpoint, String testcase) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        String testId,outputField,outputValues;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir+getAULoyaltyPropertyValue("testDataPath");
        sheet = getSheetObject(path,endpoint);
        int rowCount = getExcelRowCount(path,endpoint);
        int colCount = getExcelColumnCount(path,endpoint);
        System.out.println(rowCount);
        System.out.println(colCount);
        for(int i=2;i<rowCount;i++){
            //Excel column initial value
            int j = 1;
            testId = sheet.getRow(i).getCell(j).toString();
            outputField = sheet.getRow(i).getCell(j+3).toString();
            outputValues = sheet.getRow(i).getCell(j+4).toString();
            System.out.println(testId);
            if(testId.equalsIgnoreCase(testcase)){
                if(!outputField.contains(";")){
                    dataMap.put(outputField,outputValues);
                }else{
                    // Following code will be executed incase of more than one field value is passed for testcase
                    String[] fieldArr = outputField.split(";");
                    String[] valuesArr = outputValues.split(";");
                    if(outputValues.contains(";")) {
                        for (int r = 0; r < fieldArr.length; r++) {
                            dataMap.put(fieldArr[r].toString(), valuesArr[r].toString());
                        }
                    }else{
                        for (int r = 0; r < fieldArr.length; r++) {
                            dataMap.put(fieldArr[r].toString(), "NotNull");
                        }
                    }
                }
            }
        }
        System.out.println("Expected response body fields and values :"+dataMap);
        return dataMap;
    }

}

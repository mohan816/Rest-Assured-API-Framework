package com.utils;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class Basecode extends LoadProperties {
    private ScenarioContext context;
    private Sheet sheet;
    private XSSFWorkbook wb;

    public static WebDriver driver;
    private PropertiesConfiguration config = new PropertiesConfiguration("configAULoyalty.properties");
    private PropertiesConfiguration latamconfig = new PropertiesConfiguration("configLatam.properties");

    public Basecode() throws ConfigurationException {
    }

    public String getServerUrl() throws Exception {
        String apiUrl = null;
        System.out.println(System.getProperty("environment"));
        switch ("QA") {
            case "QA":
                apiUrl = getAULoyaltyPropertyValue("QA_apiserver") +
                        getAULoyaltyPropertyValue("QA_apiversion");
                break;
            case "UAT":
                apiUrl = getAULoyaltyPropertyValue("UAT_apiserver") +
                        getAULoyaltyPropertyValue("UAT_apiversion");
                break;
            default:
                apiUrl = getAULoyaltyPropertyValue("QA_apiserver") +
                        getAULoyaltyPropertyValue("QA_apiversion");
        }
        return apiUrl;
    }

    public String getlatamServerUrl() throws Exception {
        String apiUrl = null;
        String Project = getLatamColumbiaPropertyValue("Project");
        if (Project.equalsIgnoreCase("WhatsApp")) {
            System.out.println(System.getProperty("environment"));
            switch ("QA") {
                case "QA":
                    apiUrl = readCommonConfigProperty("QA_WhatsAppapiserver") +
                            readCommonConfigProperty("QA_apiversion");
                    break;
                case "UAT":
                    apiUrl = readCommonConfigProperty("UAT_apiserver") +
                            readCommonConfigProperty("UAT_apiversion");
                    break;
                default:
                    apiUrl = readCommonConfigProperty("QA_WhatsAppapiserver") +
                            readCommonConfigProperty("QA_apiversion");
            }
        } else {
            System.out.println(System.getProperty("environment"));
            switch ("QA") {
                case "QA":
                    apiUrl = readCommonConfigProperty("QA_apiserver") +
                            readCommonConfigProperty("QA_apiversion");
                    break;
                case "UAT":
                    apiUrl = readCommonConfigProperty("UAT_apiserver") +
                            readCommonConfigProperty("UAT_apiversion");
                    break;
                default:
                    apiUrl = readCommonConfigProperty("QA_apiserver") +
                            readCommonConfigProperty("QA_apiversion");
            }
        }

        return apiUrl;
    }

    /**
     * Method to get header only with SiteId
     *
     * @return map<String, String>
     * @throws Exception
     */
    public Map<String, String> getSiteIdHeaderAsMap() throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("siteId", getAULoyaltyPropertyValue("siteid"));
        headerMap.put("okta-accesstoken", getAULoyaltyPropertyValue("okta-accesstoken"));

        System.out.println("Site Id as API request header is - " + headerMap);
        return headerMap;
    }

    // Java program generate a random AlphaNumeric String
    // using Math.random() method
    public String generateRandomString(int n) {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz"
                + "0123456789";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    // Following util function is to generate two digit number
    public int generateTwoDigitNumber() {
        Random rand = new Random();
        int rndmNum = 10 + rand.nextInt(20);
        System.out.println("Generated 2 digit number is " + rndmNum);
        return rndmNum;
    }

    // Following util function is to generate one digit number
    public int generateOneDigitNumber() {
        Random rand = new Random();
        int rndmNum = 1 + rand.nextInt(9);
        System.out.println("Generated 1 digit number is " + rndmNum);
        return rndmNum;
    }

    // @Before
    //  public void setup() {
    // Initialize the webdriver and open the browser
    //System.setProperty("webdriver.gecko.driver", driverPath);
    //WebDriverManager.chromedriver().setup();
    //driver = new ChromeDriver();
    //driver.manage().window().maximize();

    // }
    //get current date time with Date()
    public String getCurrentSystemDate_Time() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        //get current date time with Date()
        Date date = new Date();
        String date1 = dateFormat.format(date);
        return date1;
    }

    // Funcation to compare two equal dates
    public boolean comparetwoequaldates(String date1, String date2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        Date dateFormat1 = format.parse(date1);
        Date dateFormat2 = format.parse(date2);
        Boolean res_dateCompare = (dateFormat1.compareTo(dateFormat2) == 0);
        return res_dateCompare;
    }

    public boolean verifyElementispresent(String text) {
        if (driver.getPageSource().contains(text)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Wait until element visible
     *
     * @param element
     * @return true or false
     * //  * @throws FrameworkException
     */
    public Boolean waitUntilElementVisible(WebElement element) throws Exception {
        Boolean isVisible = false;
        FluentWait<WebDriver> fluentWait =
                new FluentWait<WebDriver>(driver)
                        .withTimeout(Duration.ofSeconds(getelementWaitDuration()))
                        .pollingEvery(Duration.ofSeconds(3))
                        .ignoring(NoSuchElementException.class);
        WebElement elementVisi = fluentWait.until(ExpectedConditions.visibilityOf(element));
        if (elementVisi.isDisplayed()) {
            isVisible = true;
            //  log.info("Element is visible " + elementVisi.isDisplayed());
        } else {
            //  log.error("Unable to find element after waiting for " + 90 + "seconds");
        }
        return isVisible;
    }

    // Get Last day of Month
    public String getlastdayofMonth() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        Date lastDayOfMonth = calendar.getTime();
        DateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");
        // System.out.println("Today            : " + sdf.format(today));
        String lastDateOfMonth = sdf.format(lastDayOfMonth);
        System.out.println("Last Day of Month: " + sdf.format(lastDayOfMonth));
        return lastDateOfMonth;
    }

    public String getnextday(int amt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");
        Calendar c = Calendar.getInstance();
        Date today = new Date();
        //c.setTime(new Date());// current system date
        c.setTime(today);
        String todaydate = sdf.format(today);
        c.add(Calendar.DATE, amt); // Adding 1 day
        String nextday = sdf.format(c.getTime());
        return nextday;
    }

    public String getnextweekday(int amt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");
        Calendar c = Calendar.getInstance();
        Date today = new Date();
        //c.setTime(new Date());// current system date
        c.setTime(today);
        String todaydate = sdf.format(today);
        c.add(Calendar.DATE, amt); // Adding 7 days
        String nextweek = sdf.format(c.getTime());
        return nextweek;
    }

    public String getnextMonthday() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");
        Calendar c = Calendar.getInstance();
        Date today = new Date();
        //c.setTime(new Date());// current system date
        c.setTime(today);
        String todaydate = sdf.format(today);
        c.add(Calendar.MONTH, 1); // Adding 1 Month
        String nextMonth = sdf.format(c.getTime());
        return nextMonth;
    }

    /**
     * Get the dates from WO summery page after every Action like Ack, Enr, SW, Complete
     *
     * @param inputtype ETA Time, Started Time, Completed, On Hold, Resume
     */
    public String getdatesfromWoSummery(String inputtype) {
        WebElement dates = driver.findElement(By.xpath("//label[normalize-space()=\"" + inputtype + "\"]/following-sibling::label"));
        String dtfromUI = dates.getText();
        return dtfromUI;
    }

    // code to get the previous date of given date
    public String getthepreviousday(String date) throws Exception {
        String fromDate = date;//2021-11-09 13:00:00
        //split year, month and days from the date using StringBuffer.
        StringBuffer sBuffer = new StringBuffer(fromDate);
        String year = sBuffer.substring(2, 4);
        String mon = sBuffer.substring(5, 7);
        String dd = sBuffer.substring(8, 10);
        String modifiedFromDate = dd + '/' + mon + '/' + year;
        int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date dateSelectedFrom = null;
        Date dateNextDate = null;
        Date datePreviousDate = null;
        try {
            dateSelectedFrom = dateFormat.parse(modifiedFromDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //get the previous date in String.
        String previousDate =
                dateFormat.format(dateSelectedFrom.getTime() - MILLIS_IN_DAY);
        //get the previous date in java.util.Date.
        try {
            datePreviousDate = dateFormat.parse(previousDate);
            System.out.println("Previous day's date: " + datePreviousDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String strDate = dateFormat.format(datePreviousDate);
        return strDate;
    }

    // Function to updated time values
    public String[] timePicker(String inpDate, int inputHour, int inputMin) throws Exception {
        //input: 11-Nov-2021 12:38 AM and addition or deletion hour and min
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
        Date date = dateFormat.parse(inpDate);
        System.out.println("Input Date " + dateFormat.format(date));

        // Convert Date to Calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 0);
        c.add(Calendar.MONTH, 0);
        c.add(Calendar.DATE, 0);
        c.add(Calendar.HOUR, inputHour);
        c.add(Calendar.MINUTE, inputMin);

        // Convert calendar back to Date
        Date currentDatePlusOne = c.getTime();

        String upDate = dateFormat.format(currentDatePlusOne);
        System.out.println("Converted Date " + upDate);

        //Splitting time to required format
        String formatAMorPM = upDate.split(" ")[2];
        String time = upDate.split(" ")[1];
        formatAMorPM = formatAMorPM.toUpperCase();

        String singleHr = time.split(":")[0];
        String singleMin = time.split(":")[1];

        String[] TimeValues = new String[3];
        TimeValues[0] = singleHr;
        TimeValues[1] = singleMin;
        TimeValues[2] = formatAMorPM;
        return TimeValues;

    }

    public void waituntilelementvisible_click(WebElement element) throws Exception {
        Boolean isVisible = false;
        FluentWait<WebDriver> fluentWait =
                new FluentWait<WebDriver>(driver)
                        .withTimeout(Duration.ofSeconds(getelementWaitDuration()))
                        .pollingEvery(Duration.ofSeconds(3))
                        .ignoring(NoSuchElementException.class);
        WebElement elementVisi = fluentWait.until(ExpectedConditions.visibilityOf(element));
        if (elementVisi.isDisplayed()) {
            element.click();
        } else {
            //  log.error("Unable to find element after waiting for " + 90 + "seconds");
        }
    }

    // validate the amount field of Tiles
    public void amtFieldValidation(String textfield, int decimalvalue) throws Exception {
        String txtintpart = null;
        int decimalpart = 0;
        int decimaldetect = textfield.indexOf(".");
        if (decimaldetect >= 0) {
            String[] splitter = textfield.split("\\.");
            txtintpart = splitter[0];
            decimalpart = splitter[1].length();
        } else {
            txtintpart = textfield;
        }
        String amtvalue = txtintpart.replaceAll("[$+^]*", "");
        boolean br = false;
        for (int j = 0; j <= 1; j++) {
            if (txtintpart.charAt(j) == '$') {
                br = true;
            }
        }
        assertThat(br).as("amt filed prefix is nt $").isTrue();
        int counter = 0;
        for (int i = 0; i < amtvalue.length() - 1; i++) {
            if (amtvalue.charAt(i) == ',') {
                counter++;
            }
        }
        int count = amtvalue.length() - amtvalue.replace(",", "").length();
        assertThat(counter == count).as("Decimal Separator is not placed properly").isTrue();
        if (decimalvalue == 0) {
            assertThat(decimalpart == decimalvalue).as("Decimal value present for amount").isTrue();
        } else if (decimalvalue == 2) {
            assertThat(decimalpart == decimalvalue).as("Decimal value is not 2 digit").isTrue();
        }
    }

    /**
     * @param textfield    Percentage value with 1 or 2 decimals fields
     * @param decimalvalue 1 or 2
     */
    // Percentage field validation
    public void percentageFieldValidation(String textfield, int decimalvalue) throws Exception {
        String percentagefield = textfield;
        assertThat(percentagefield.endsWith("%")).as("Percentage field is not ended with % symbol").isTrue();
        String txtPerVal = textfield.replaceAll("[-%+^]*", "");
        String[] splitter = txtPerVal.split("\\.");
        int decimalpart = splitter[1].length();
        if (decimalvalue == 1) {
            assertThat(decimalpart == decimalvalue).as("Decimal value is more than 1 digit").isTrue();
        } else if (decimalvalue == 2) {
            assertThat(decimalpart == decimalvalue).as("Decimal value is more than 2 digit").isTrue();
        }

    }

    /**
     * @param columnIndex 0-Comsale or 1-Comp%LW or 2-Comp%L4W, 3-Comp%Plan, 4-OHHand, 5-OHMargin,6-OOMargin,7-MarginPln 8-OTB
     */
    public void sortbtnvalidation(List<WebElement> tablerowxpath, int columnIndex, WebElement sortbtn) throws Exception {
        List<String> bfSortlist = new ArrayList<String>();
        List<String> aftSortlist = new ArrayList<String>();
        int colIn = columnIndex;
        for (int j = 0; j <= 5; j++) {
            List<WebElement> colums = tablerowxpath.get(j).findElements(By.tagName("td"));
            String values = colums.get(colIn).getText();
            System.out.println("Values extracted:" + values);
            bfSortlist.add(values);
        }
        waitUntilElementVisible(sortbtn);
        sortbtn.click();

        for (int j = 0; j <= 5; j++) {
            List<WebElement> colums = tablerowxpath.get(j).findElements(By.tagName("td"));
            String values = colums.get(colIn).getText();
            System.out.println("Values extracted:" + values);
            aftSortlist.add(values);
        }
        waitUntilElementVisible(sortbtn);
        sortbtn.click();
        assertThat(bfSortlist.equals(aftSortlist)).as("Sort didnt happen properly for column Index:" + columnIndex + "").isFalse();
    }

    // Read JSON file data
    public Object jsonFileReader(String jsonFilePath) throws Exception {
        JSONParser jsonParser = new JSONParser();
        Object fileData;
        try (FileReader reader = new FileReader(jsonFilePath)) {
            //Read JSON file
            fileData = jsonParser.parse(reader);
        }
        return fileData;
    }

    // Read Excel File with number of rows present on a sheet
    public int getExcelRowCount(String excelFilePath, String sheetName) throws Exception {
        FileInputStream fis = new FileInputStream(new File(excelFilePath));
        //workbook instance that refers to .xlsx file
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        //Worksheet object to retrieve the object
        XSSFSheet sheet = wb.getSheet(sheetName);
        return sheet.getPhysicalNumberOfRows();
    }

    // Read Excel File with number of columns present on a sheet
    public int getExcelColumnCount(String excelFilePath, String sheetName) throws Exception {
        XSSFRow row = null;
        FileInputStream fis = new FileInputStream(new File(excelFilePath));
        //Workbook instance that refers to .xlsx file
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        //Worksheet object to retrieve the object
        XSSFSheet sheet = wb.getSheet(sheetName);
        row = sheet.getRow(0);
        int columnCount = row.getLastCellNum();
        return columnCount;
    }

    // Read specific cell value on a sheet
    public Sheet getSheetObject(String excelFilePath, String sheetName) throws Exception {
        XSSFRow row = null;
        FileInputStream fis = new FileInputStream(new File(excelFilePath));
        //Workbook instance that refers to .xlsx file
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        //Worksheet object to retrieve the object
        XSSFSheet sheet = wb.getSheet(sheetName);
        return sheet;
    }

    // Read specific cell value on a sheet
    public XSSFWorkbook getExcelObject(String excelFilePath) throws Exception {
        XSSFRow row = null;
        FileInputStream fis = new FileInputStream(new File(excelFilePath));
        //Workbook instance that refers to .xlsx file
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        return wb;
    }

    // Generate Alphanumeric string for given size
    public String getAlphaNumericString(int n) throws Exception {
        // chose a Character random from this String
        String AlphaString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";
        String NumericString = "0123456789";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int indexAlpha
                    = (int) (AlphaString.length()
                    * Math.random());
            int indexNum
                    = (int) (NumericString.length()
                    * Math.random());
            sb.append(AlphaString
                    .charAt(indexAlpha));
            sb.append(NumericString
                    .charAt(indexNum));
        }
        return sb.toString();
    }

    public String getNumericString(int n) throws Exception {
        // chose a Character random from this String
        String NumericString = "123456789";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {

            int indexNum
                    = (int) (NumericString.length()
                    * Math.random());
            sb.append(NumericString
                    .charAt(indexNum));
        }
        return sb.toString();
    }

    /**
     * Method to update the value in the test data
     *
     * @return response
     */
    public void udpateValueTestDataSheet(String endpoint, String testcase, String value) throws Exception {
        HashMap<String, Object> dataMap = new HashMap<>();
        String testId = null;
        String usrdir = System.getProperty("user.dir");
        String path = usrdir + getAULoyaltyPropertyValue("testDataPath");
        wb = getExcelObject(path);
        sheet = getSheetObject(path, endpoint);
        int rowCount = getExcelRowCount(path, endpoint);
        int colCount = getExcelColumnCount(path, endpoint);
        System.out.println(rowCount);
        System.out.println(colCount);
        for (int i = 2; i < rowCount; i++) {
            //Excel column initial value
            int j = 1;
            testId = sheet.getRow(i).getCell(j).toString();
            System.out.println(testId);
            if (testId.equalsIgnoreCase(testcase)) {
                XSSFCell cell = (XSSFCell) sheet.getRow(i).getCell(6);
                cell.setCellValue(value);
                cell.setCellType(CellType.STRING);
                wb.close();
            }
        }
        System.out.println("Updated sheet : " + sheet.getSheetName() + " for testcase " + testId + " with value " + value);
    }

    public void updateConfigValue(String key, String value) throws Exception {
        config.setProperty(key, value);
        config.save();
        System.out.println("Updated key " + key + " value as " + value);
        switch (key) {
            case "newPassword":
                config.setProperty(key, value);
                config.save();
                System.out.println("Updated key " + key + " value as " + value);
                break;
            case "SessionToken":
                config.setProperty(key, value);
                config.save();
                System.out.println("Updated key " + key + " value as " + value);
                break;
            case "AccessToken":
                config.setProperty(key, value);
                config.save();
                System.out.println("Updated key " + key + " value as " + value);
                break;
            case "orderHistTotal":
                latamconfig.setProperty(key, value);
                latamconfig.save();
                System.out.println("Updated key " + key + " value as " + value);
                break;
        }
    }

    public void updaterewardValue(String key, int value) throws Exception {
        config.setProperty(key, value);
        config.save();
        System.out.println("Updated key " + key + " value as " + value);
    }

    public void updatelatamConfigValue(String key, String value) throws Exception {
        latamconfig.setProperty(key, value);
        latamconfig.save();
        System.out.println("Updated key " + key + " value as " + value);
    }

    public void updatelatamquantityValue(String key, int value) throws Exception {
        latamconfig.setProperty(key, value);
        latamconfig.save();
        System.out.println("Updated key " + key + " value as " + value);

    }

    public void updateAccessToken(String key, String value) throws Exception {
        latamconfig.setProperty(key, value);
        latamconfig.save();
        System.out.println("Updated key " + key + " value as " + value);
        switch (key) {
            case "SessionToken":
                latamconfig.setProperty(key, value);
                latamconfig.save();
                System.out.println("Updated key " + key + " value as " + value);
                break;
            case "LatamAccessToken":
                latamconfig.setProperty(key, value);
                latamconfig.save();
                System.out.println("Updated key " + key + " value as " + value);
                break;
        }
    }

    public String getFutureDate() throws Exception {
        Date date = DateUtils.addDays(new Date(), 7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * Method to update header with SiteId and xApiKey
     *
     * @return map<String, String>
     * @throws Exception
     */
    public HashMap<String, String> updateSiteIdxAPIKeyHeaderForProject(String project) throws Exception {
        HashMap<String, String> headerMap = new HashMap<>();
        switch (project) {
            case "columbia":
                headerMap.put("siteId", getLatamColumbiaPropertyValue("siteid"));
                headerMap.put("x-api-key", getLatamColumbiaPropertyValue("x-api-key"));
                break;
            case "brazil":
                headerMap.put("siteId", getLatamBrazilPropertyValue("siteid"));
                headerMap.put("x-api-key", getLatamBrazilPropertyValue("x-api-key"));
                break;
            case "WhatsApp":
                headerMap.put("siteId", getLatamColumbiaPropertyValue("WhatsAppsiteid"));
                headerMap.put("x-api-key", getLatamColumbiaPropertyValue("WhatsApp-x-api-key"));
                break;
        }

        System.out.println("Updated request header for " + project + " and the header is - " + headerMap);
        return headerMap;
    }

//    public String getAppServerForProject(String project) throws Exception {
//        String apiServer;
//        switch (project) {
//            case "columbia":
//                apiServer = getLatamColumbiaPropertyValue("QA_apiserver")+getLatamColumbiaPropertyValue("QA_apiversion");
//                break;
//            case "brazil":
//                apiServer = getLatamBrazilPropertyValue("QA_apiserver")+getLatamBrazilPropertyValue("QA_apiversion");
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + project);
//        }
//
//        System.out.println("Constructed URL for " + project + " is - " + apiServer);
//        return apiServer;
//    }


    /**
     * Method to update header based on testdata
     *
     * @return map<String, String>
     * @throws Exception
     */
    public HashMap<String, String> updateHeaderBasedonTestdata(String loginWith, HashMap<String, String> headerMap, HashMap<String, Object> dataMap) throws Exception {

        for (Map.Entry mapElement : dataMap.entrySet()) {
            String testField = (String) mapElement.getKey();
            String testVal = (String) dataMap.get(testField);
            if (!testVal.equalsIgnoreCase("")) {
                if (loginWith.equalsIgnoreCase("accesstoken")) {
                    if (testField.equalsIgnoreCase("okta-accesstoken")) {
                        headerMap.put(testField, getAULoyaltyPropertyValue("LatamAccessToken"));
                    } else {
                        headerMap.put(testField, testVal);
                    }

                } else {
                    headerMap.put(testField, testVal);
                }

                System.out.println("Updated header node :" + headerMap);
            } else {
                headerMap.put(testField, "");
                System.out.println("Updated header node :" + headerMap);
            }
        }
        System.out.println("Site Id as API request header is - " + headerMap);

        System.out.println("Updated request header is - " + headerMap);
        return headerMap;
    }
}

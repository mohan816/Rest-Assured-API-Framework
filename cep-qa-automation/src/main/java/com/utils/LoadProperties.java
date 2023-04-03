package com.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
    static String auLoyaltyPropertiesFile = "configAULoyalty.properties";
    static String latamColumbiaPropertiesFile = "configLatam.properties";
    static String latamBrazilPropertiesFile = "configB2BLatamBR.properties";
    static String commonConfigPropertiesFile = "commonConfig.properties";
    private static Properties confProperties = new Properties();
    public static final Logger logger = Logger.getLogger("devpinoyLogger");

    private static void loadPropFromResource(String fileName) throws Exception {
        try {
            InputStream inputStream = LoadProperties.class.getClassLoader().getResourceAsStream(fileName);
            new FileReader(fileName);
            InputStream inputStream1 = new FileInputStream(fileName);
            confProperties.load(inputStream1);
        } catch (IOException var4) {
            var4.printStackTrace();
            logger.error("Failed  to load prop files -- " + var4);
        }
    }
    public static String getCommonConfigPropertyValue(String propertyKey) throws Exception {
        String propertyValue;
        propertyValue = readCommonConfigProperty(propertyKey);
        return propertyValue;
    }
    public static String getAULoyaltyPropertyValue(String propertyKey) throws Exception {
        String propertyValue;
        propertyValue = readAULoyaltyConfigProperty(propertyKey);
        return propertyValue;
    }
    public static String getLatamColumbiaPropertyValue(String propertyKey) throws Exception {
        String propertyValue;
        propertyValue = readLatamColumbiaConfigProperty(propertyKey);
        return propertyValue;
    }
    public static String getLatamBrazilPropertyValue(String propertyKey) throws Exception {
        String propertyValue;
        propertyValue = readLatamBrazilConfigProperty(propertyKey);
        return propertyValue;
    }
    public static String readAULoyaltyConfigProperty(String key) {
        String value = null;

        try {
            loadPropFromResource(auLoyaltyPropertiesFile);
            value = confProperties.getProperty(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            logger.error("Unable to find property - " + key + " check the properties file---" + var3);
        }

        return value;
    }

    public static String readLatamColumbiaConfigProperty(String key) {
        String value = null;

        try {
            loadPropFromResource(latamColumbiaPropertiesFile);
            value = confProperties.getProperty(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            logger.error("Unable to find property - " + key + " check the properties file---" + var3);
        }

        return value;
    }

    public static String readLatamBrazilConfigProperty(String key) {
        String value = null;

        try {
            loadPropFromResource(latamBrazilPropertiesFile);
            value = confProperties.getProperty(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            logger.error("Unable to find property - " + key + " check the properties file---" + var3);
        }

        return value;
    }

    public static String readCommonConfigProperty(String key) {
        String value = null;

        try {
            loadPropFromResource(commonConfigPropertiesFile);
            value = confProperties.getProperty(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            logger.error("Unable to find property - " + key + " check the properties file---" + var3);
        }

        return value;
    }

    public static int getelementWaitDuration() throws Exception {
        String eleWaitDur = getAULoyaltyPropertyValue("elementWaitDuration");
        return Integer.parseInt(eleWaitDur);
    }
    public static int getLatamColumbiaelementWaitDuration() throws Exception {
        String eleWaitDur = getLatamColumbiaPropertyValue("elementWaitDuration");
        return Integer.parseInt(eleWaitDur);
    }

    public static Boolean isPropertyKeyPresent(String keyName) {
        boolean isKeyPresent = false;
        if (confProperties.containsKey(keyName)) {
            isKeyPresent = true;
        }
        return isKeyPresent;
    }

    public static String setauLoyaltyConfigProperty(String key, String value) {
        try {
            loadPropFromResource(auLoyaltyPropertiesFile);
            confProperties.setProperty(key,value);
            System.out.println("Updated config property key "+key+" value as "+value);
        } catch (Exception var3) {
            var3.printStackTrace();
            logger.error("Unable to find property - " + key + " check the properties file---" + var3);
        }
        return value;
    }
    public static String setlatamColumbiaConfigProperty(String key, String value) {
        try {
            loadPropFromResource(latamColumbiaPropertiesFile);
            confProperties.setProperty(key,value);
            System.out.println("Updated config property key "+key+" value as "+value);
        } catch (Exception var3) {
            var3.printStackTrace();
            logger.error("Unable to find property - " + key + " check the properties file---" + var3);
        }
        return value;
    }

    public static String getProject() throws Exception {
        String project;
        if (System.getProperty("project") == null) {
            project = getCommonConfigPropertyValue("project");
        } else {
            project = System.getProperty("project");
        }
        return project;
    }

    public String getAppServerForProject(String project) throws Exception {
        String apiServer;
        switch (getProject()) {
            case "columbia":
                apiServer = readCommonConfigProperty("QA_apiserver")+readCommonConfigProperty("QA_apiversion");
                break;
            case "brazil":
                apiServer = getLatamBrazilPropertyValue("QA_apiserver")+getLatamBrazilPropertyValue("QA_apiversion");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + project);
        }

        System.out.println("Constructed URL for " + project + " is - " + apiServer);
        return apiServer;
    }

}



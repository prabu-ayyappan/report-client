package com.cognizant.dashboardclient.plugins.common;

import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.cognizant.dashboardclient.plugins.common.BaseConstants.*;

/**
 * Created by 784420 on 3/18/2020 6:57 PM
 */
public class TProperties extends HashMap<String, String> {
    private static TProperties properties ;
    Properties systemProperties = System.getProperties();
    private TProperties() {}
    private Map<? extends String, ? extends String> getProp(){
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream(BaseConstants.LEAP_COLLECTOR_PROPERTIES));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*System Properties*/
        String projectName = (String) systemProperties.get(PROJECT_NAME);
        String executionName = (String) systemProperties.get(EXECUTION_NAME);
        String testReportURL = (String) systemProperties.get(TEST_REPORT_URL);
        String leapEnable = (String) systemProperties.get(LEAP_ENABLE);
        String testReportToken = (String) systemProperties.get(TEST_REPORT_TOKEN);

        if (!StringUtils.isEmpty(projectName)) prop.put(PROJECT_NAME, projectName);
        if (!StringUtils.isEmpty(executionName)) prop.put(EXECUTION_NAME, executionName);
        if (!StringUtils.isEmpty(testReportURL)) prop.put(TEST_REPORT_URL, testReportURL);
        if (!StringUtils.isEmpty(leapEnable)) prop.put(LEAP_ENABLE, leapEnable);
        if (!StringUtils.isEmpty(testReportToken)) prop.put(TEST_REPORT_TOKEN, testReportToken);

        return (Map)prop;
    }

    public static TProperties getInstance() {
        if (properties == null){
            properties = new TProperties();
            properties.putAll(properties.getProp());
        }
        return properties;
    }

    public static TProperties getInstance(String fileName) {
        properties = new TProperties();
        properties.putAll(readProperties(fileName));
        return properties;
    }
    private static Map<? extends String, ? extends String> readProperties(String propertiesFile) {
        Properties properties = new Properties();
        File rp = new File(propertiesFile);
        if (rp.exists()) {
            try {
                FileReader fr = new FileReader(rp);
                properties.load(fr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (Map)properties;
    }

}

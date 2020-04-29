package com.cognizant.dashboardclient.plugins.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {

    public static void logText(String logText) {
        log.info(logText);
    }

    public static boolean isLeapEnabled(){
        TProperties tProperties = TProperties.getInstance();
        return Boolean.parseBoolean(tProperties.getOrDefault(BaseConstants.LEAP_ENABLE, String.valueOf(true)));
    }

    public static void jsonString(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String writeValueAsString = mapper.writeValueAsString(object);
            log.info(writeValueAsString);
        } catch (JsonProcessingException e) {
            log.error("Exception while json parsing :", e);
        }
    }
    public static String returnJsonString(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String writeValueAsString = mapper.writeValueAsString(object);
            return writeValueAsString;
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static String getLastIndexValue(String value, String delimiter) {
        if (value == null || value.equals("") || !value.contains(delimiter)) {
            return value;
        }
        int lastIndex = value.lastIndexOf(delimiter);
        if (value.length() <= lastIndex+1){
            return value;
        }
        return value.substring(lastIndex+1);
    }

}

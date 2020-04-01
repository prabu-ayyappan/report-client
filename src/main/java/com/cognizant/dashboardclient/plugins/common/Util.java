package com.cognizant.dashboardclient.plugins.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {

    private Util() {
    }

    public void logText(String logText) {
        log.info(logText);
    }

    public void jsonString(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String writeValueAsString = mapper.writeValueAsString(object);
            log.info(writeValueAsString);
        } catch (JsonProcessingException e) {
            log.error("Exception while json parsing :", e);
        }
    }
}

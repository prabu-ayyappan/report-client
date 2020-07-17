package com.cognizant.dashboardclient.plugins.clients;

import com.cognizant.dashboardclient.plugins.common.BaseConstants;
import com.cognizant.dashboardclient.plugins.common.TProperties;
import feign.Feign;
import feign.Logger;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.util.HashMap;
import java.util.Map;

public class TestReportMain {

    public static TestReportClient getTestReportClient(String feignURL){
        return Feign.builder()
                .contract(new SpringMvcContract())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(TestReportClient.class))
                .logLevel(Logger.Level.NONE)  // changed to NONE instead of FULL
                .target(TestReportClient.class, feignURL);
    }

    public static TestReportClient getUploadClient(){
        TProperties properties = TProperties.getInstance();
        String feignURL = properties.get(BaseConstants.TEST_REPORT_URL);

        return Feign.builder()
                .contract(new SpringMvcContract())
                .encoder(new SpringFormEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(TestReportClient.class))
                .logLevel(Logger.Level.NONE)  // changed to NONE instead of FULL
                .target(TestReportClient.class, feignURL);
    }

    public static Map<String, String> getHeaders(){
        TProperties properties = TProperties.getInstance();
        String token = properties.getOrDefault(BaseConstants.TEST_REPORT_TOKEN, "");
//        String bearerToken = String.format("Bearer %s", token);
        return new HashMap<String, String>(){{
            //put("Authorization", bearerToken);
            put(BaseConstants.CUSTOM_TOKEN_ID, token);
        }
        };
    }

    public static Map<String, String> getUploadHeaders(){

        TProperties properties = TProperties.getInstance();
        String token = properties.get(BaseConstants.TEST_REPORT_TOKEN);
//        String bearerToken = String.format("Bearer %s", token);
        HashMap<String, String> map = new HashMap<>();
//        map.put("Authorization", bearerToken);
        map.put(BaseConstants.CUSTOM_TOKEN_ID, token);
        map.put("Content-Type", "multipart/form-data;boundary=---------------------------7da24f2e50046");

        return map;
    }

}

package com.cognizant.dashboardclient.plugins.clients;

import com.cognizant.dashboardclient.plugins.common.BaseConstants;
import com.cognizant.dashboardclient.plugins.common.TProperties;
import feign.Feign;
import feign.Logger;
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
                .logLevel(Logger.Level.FULL)
                .target(TestReportClient.class, feignURL);
    }

    public static Map<String, String> getHeaders(){
        TProperties properties = TProperties.getInstance();
        String token = properties.get(BaseConstants.TEST_REPORT_TOKEN);
        String bearerToken = String.format("Bearer %s", token);
        return new HashMap<String, String>(){{
            put("Authorization", bearerToken);
        }
        };
    }
}

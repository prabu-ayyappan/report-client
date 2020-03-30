package com.cognizant.dashboardclient.plugins.clients;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

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
}

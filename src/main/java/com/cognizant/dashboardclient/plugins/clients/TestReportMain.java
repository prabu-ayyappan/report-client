package com.cognizant.dashboardclient.plugins.clients;

import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

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

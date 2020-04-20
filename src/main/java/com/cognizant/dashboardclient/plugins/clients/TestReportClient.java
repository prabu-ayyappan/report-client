package com.cognizant.dashboardclient.plugins.clients;

import com.cognizant.dashboardclient.plugins.models.ExecutiveTestPlan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface TestReportClient {
    @PostMapping(value = "/test/report/", consumes = MediaType.APPLICATION_JSON_VALUE)
    ExecutiveTestPlan InsertOrUpdateTestPlan(@RequestBody ExecutiveTestPlan executiveTestPlan);

    @PostMapping(value = "/test/report/", consumes = MediaType.APPLICATION_JSON_VALUE)
    ExecutiveTestPlan InsertOrUpdateTestPlan(@RequestBody ExecutiveTestPlan executiveTestPlan, @RequestHeader Map header);

    @RequestMapping(value = "/image/"
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , method = RequestMethod.POST)
    Map<String, String> addImage(@PathVariable("title") String title, @PathVariable("file") MultipartFile file, @RequestHeader Map header);
}

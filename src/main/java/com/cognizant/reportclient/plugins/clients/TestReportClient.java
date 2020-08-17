package com.cognizant.reportclient.plugins.clients;

import com.cognizant.reportclient.plugins.models.BaseAttachment;
import com.cognizant.reportclient.plugins.models.ExecutiveTestPlan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface TestReportClient {
    @PostMapping(value = "/test/reports", consumes = MediaType.APPLICATION_JSON_VALUE)
    ExecutiveTestPlan InsertOrUpdateTestPlan(@RequestBody ExecutiveTestPlan executiveTestPlan);

    @PostMapping(value = "/test/reports", consumes = MediaType.APPLICATION_JSON_VALUE)
    ExecutiveTestPlan InsertOrUpdateTestPlan(@RequestBody ExecutiveTestPlan executiveTestPlan, @RequestHeader Map header);

    @RequestMapping(value = "/attachments"
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE
            , method = RequestMethod.POST)
    BaseAttachment addImage(@PathVariable("name") String title, @PathVariable("file") MultipartFile file, @RequestHeader Map header);
}

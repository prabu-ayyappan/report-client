package com.cognizant.dashboardclient.plugins.clients;

import com.cognizant.dashboardclient.plugins.models.ExecutiveTestPlan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TestReportClient {
    @PostMapping(value = "/test/report/", consumes = MediaType.APPLICATION_JSON_VALUE)
    ExecutiveTestPlan InsertOrUpdateTestPlan(@RequestBody ExecutiveTestPlan executiveTestPlan);
}

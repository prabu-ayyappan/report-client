package com.cognizant.dashboardclient.plugins.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 784420 on 3/18/2020 2:00 PM
 */
@Data
public class ExecutiveTestPlan {
    private String id;
    private String projectName;
    private String toolName;
    private Date startTime;
    private Date endTime;
    private AtomicInteger testSuitCount;
    private AtomicInteger testCaseCount;
    private List<TestSuite> testSuites;
}

package com.cognizant.dashboardclient.plugins.models;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by 784420 on 3/18/2020 2:00 PM
 */
@Data
public class ExecutiveTestPlan {
    private String id;
    private String projectName;
    private String name;
    private String toolName;
    private Date startTime;
    private Date endTime;

    private AtomicInteger testSuitCount;
    private AtomicInteger testCaseCount;
    private List<TestSuite> testSuites;

    private AtomicLong failed;
    private AtomicLong passed;
    private AtomicLong skipped;
    private List<String> logs;
}

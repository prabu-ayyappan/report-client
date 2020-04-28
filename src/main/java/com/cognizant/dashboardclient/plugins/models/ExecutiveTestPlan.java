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
    private long executionNumber;
    private String description;
    private String toolName;
    private Date startTime;
    private Date endTime;
    protected long duration;

    private AtomicInteger testSuitCount = new AtomicInteger();
    private AtomicInteger testCaseCount = new AtomicInteger();
    private List<TestSuite> testSuites;

    private AtomicLong failed = new AtomicLong();
    private AtomicLong passed = new AtomicLong();
    private AtomicLong skipped = new AtomicLong();
    private List<String> logs;
}

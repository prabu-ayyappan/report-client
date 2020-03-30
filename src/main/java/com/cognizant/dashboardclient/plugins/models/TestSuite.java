package com.cognizant.dashboardclient.plugins.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by 784420 on 3/9/2020 7:48 PM
 */
@Data
@NoArgsConstructor
public class TestSuite {
    private List<TestCase> testCases;
    private String packageName;
    private String name;
    private String result;
    private Date startTime;
    private Date endTime;
    private AtomicLong duration;
    private AtomicInteger testsCount;

    private AtomicLong failed;
    private AtomicLong passed;
    private AtomicLong skipped;

    private String uniqueId;
    private String parentId;
    private String remarks;
    private List<String> logs;

    @JsonIgnore
    public TestSuite(String packageName, String uniqueId, String name) {
        this.packageName = packageName;
        this.name = name;
        this.uniqueId = uniqueId;
        this.result = StatusEnum.PASSED.name();
        this.duration = new AtomicLong(0L);
        this.testsCount = new AtomicInteger(0);

        this.failed = new AtomicLong(0);
        this.passed = new AtomicLong(0);
        this.skipped = new AtomicLong(0);

        this.testCases = new ArrayList<>();
    }

    @JsonIgnore
    public void addSuccessCase(TestCase aCase){
        this.addCase(aCase);
        this.passed.incrementAndGet();
    }

    @JsonIgnore
    public void addFailCase(TestCase aCase){
        this.addCase(aCase);
        this.failed.incrementAndGet();
    }

    @JsonIgnore
    public void addIgnoreCase(TestCase aCase){
        this.addCase(aCase);
        this.skipped.incrementAndGet();
    }
    @JsonIgnore
    public void addCase(TestCase aCase) {
        this.testCases.add(aCase);
        this.testsCount.incrementAndGet();
        this.duration.addAndGet(aCase.getDuration());
        if (aCase.getResult().equals(StatusEnum.FAILED.name())){
            this.result = StatusEnum.FAILED.name();
        }
    }
}

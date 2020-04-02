package com.cognizant.dashboardclient.plugins.models;

import java.util.Date;
import java.util.List;

public class TestStep {
    private String name;
    private long duration;
    private String description;
    private Date startTime;
    private Date endTime;
    private String result;
    private String uniqueId;
    private String parentId;
    private String remarks;
    private String exceptionStacktrace;
    private List<String> logs;
}

package com.cognizant.reportclient.plugins.models;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TestStep {
    private String name;
    private long duration;
    private String description;
    private Date startTime;
    private Date endTime;
    private String result;
    private String uniqueId;
    private String parentId;
    private List<BaseAttachment> attachments;
    private Object argument;
    private String remarks;
    private String exceptionStacktrace;
    private List<LogDetails> logs;
}

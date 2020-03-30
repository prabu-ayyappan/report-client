package com.cognizant.dashboardclient.plugins.models;

import lombok.Data;

import java.util.Date;

/**
 * Created by 784420 on 3/9/2020 7:41 PM
 */
@Data
public class TestCase {
    protected String name;
    protected long duration;
    private String description;
    private Date startTime;
    private Date endTime;
    protected String result;
    private String uniqueId;
    private String parentId;
    private String remarks;
    private String exceptionStacktrace;
}

package com.cognizant.reportclient.plugins.models;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LogDetails {
    public Date timestamp;
    public String level;
    public LogSystemInfo systemInfo;
    public List<BaseAttachment> attachments = null;
    public String message;
}
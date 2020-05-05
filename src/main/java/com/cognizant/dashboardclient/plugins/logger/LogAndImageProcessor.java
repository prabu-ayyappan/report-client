package com.cognizant.dashboardclient.plugins.logger;

import com.cognizant.dashboardclient.plugins.clients.ClientManager;
import com.cognizant.dashboardclient.plugins.common.BaseConstants;
import com.cognizant.dashboardclient.plugins.common.SystemInfoUtil;
import com.cognizant.dashboardclient.plugins.models.BaseAttachment;
import com.cognizant.dashboardclient.plugins.models.LogDetails;
import com.cognizant.dashboardclient.plugins.models.LogSystemInfo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public class LogAndImageProcessor {
    private static LogSystemInfo systemInfo;
    private List<BaseAttachment> attachments = new ArrayList<>();
    private List<LogDetails> logs = new ArrayList<>();

    public void processLogs(String loggerName, String methodName) {
        PluginLog4JAppender instance = PluginLog4JAppender.getInstance();
        if (instance == null) return;
        List<LogEvent> events = instance.getEvents(loggerName, methodName);
        processEvents(events);
    }
    public void processLogs(long startTime, long endTime) {
        PluginLog4JAppender instance = PluginLog4JAppender.getInstance();
        if (instance == null) return;
        List<LogEvent> events = instance.getEvents(startTime, endTime);
        processEvents(events);
    }
    public void processStaticLogs(File file) {
        LogDetails details = new LogDetails();
        String format = String.format("%s [%s] [%s ] ScreenShot Details - %s%n"
                , new Timestamp(System.currentTimeMillis()), "main", Level.INFO.name(), file.getAbsolutePath()
        );
        details.setMessage(format);
        details.setTimestamp(Calendar.getInstance().getTime());
        details.setLevel(Level.INFO.name());
        details.setSystemInfo(getSystemInfo());
        Object[] parameters = {file};
        try {
            BaseAttachment baseAttachment = sendImage(parameters);
            attachments.add(baseAttachment);
            details.setAttachments(Arrays.asList(baseAttachment));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logs.add(details);
    }
    private void processEvents(List<LogEvent> events){
        PluginLog4JAppender instance = PluginLog4JAppender.getInstance();
        events.forEach(event -> {
            LogDetails details = new LogDetails();

            details.setMessage(instance.getLayout().toSerializable(event).toString());
            details.setTimestamp(new Date(event.getTimeMillis()));
            details.setLevel(event.getLevel().name());
            details.setSystemInfo(getSystemInfo());

            Message message = event.getMessage();
            if (message.getFormattedMessage().startsWith(BaseConstants.LEAP_MESSAGE_FILE)){
                Object[] parameters = message.getParameters();
                if (parameters !=null && parameters.length > 0){
                    try {
                        BaseAttachment baseAttachment = sendImage(parameters);
                        attachments.add(baseAttachment);
                        details.setAttachments(Arrays.asList(baseAttachment));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            logs.add(details);
        });
    }

    private BaseAttachment sendImage(Object[] parameters) throws IOException {
        BaseAttachment baseImage = null;
        switch (parameters.length){
            case 1:
                if (parameters[0] instanceof File) {
                    File file = (File) parameters[0];
                    baseImage = sendImage(file, file.getName());
                }
                break;
            case 2:
                if (parameters[0] instanceof File) {
                    File file = (File) parameters[0];
                    String name = (String) parameters[1];
                    baseImage = sendImage(file, name);
                }
                break;
            default:
                break;

        }
        return baseImage;
    }

    private BaseAttachment sendImage(File file, String name) throws IOException {
        BaseAttachment baseImage = new BaseAttachment();
        return ClientManager.addImage(file, name);
    }

    private LogSystemInfo getSystemInfo(){
        if (systemInfo == null){
            SystemInfoUtil infoUtil = SystemInfoUtil.getInstance();
            systemInfo = new LogSystemInfo();
            systemInfo.setOs(infoUtil.getOs());
            systemInfo.setPlatform(infoUtil.getPlatform());
            systemInfo.setJava(infoUtil.getJava());
        }
        return systemInfo;
    }


    public List<BaseAttachment> getAttachments() {
        return attachments;
    }

    public List<LogDetails> getLogs() {
        return logs;
    }
}

package com.cognizant.dashboardclient.plugins.logger;

import com.cognizant.dashboardclient.plugins.clients.ClientManager;
import com.cognizant.dashboardclient.plugins.common.BaseConstants;
import com.cognizant.dashboardclient.plugins.models.BaseImage;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogAndImageProcessor {
    private List<BaseImage> images = new ArrayList<>();
    private List<String> logs = new ArrayList<>();

    public void processLogs(String loggerName, String methodName) {
        PluginLog4JAppender instance = PluginLog4JAppender.getInstance();
        if (instance == null ) return;
        List<LogEvent> events = instance.getEvents(loggerName, methodName);
        events.forEach(logEvent -> {
            Message message = logEvent.getMessage();
            if (message.getFormattedMessage().startsWith(BaseConstants.LEAP_MESSAGE_FILE)){
                Object[] parameters = message.getParameters();
                if (parameters !=null && parameters.length > 0){
                    try {
                        images.add(sendImage(parameters));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            logs.add(instance.getLayout().toSerializable(logEvent).toString());
        });
    }

    private BaseImage sendImage(Object[] parameters) throws IOException {
        BaseImage baseImage = null;
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
                    String title = (String) parameters[1];
                    baseImage = sendImage(file, title);
                }
                break;
            default:
                break;

        }
        return baseImage;
    }

    private BaseImage sendImage(File file, String title) throws IOException {
        BaseImage baseImage = new BaseImage();
        Map<String, String> map = ClientManager.addImage(file, title);
        baseImage.setId(map.get("id"));
        baseImage.setTitle(title);
        return baseImage;
    }


    public List<BaseImage> getImages() {
        return images;
    }

    public List<String> getLogs() {
        return logs;
    }
}

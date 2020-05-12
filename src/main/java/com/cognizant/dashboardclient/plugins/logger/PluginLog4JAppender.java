package com.cognizant.dashboardclient.plugins.logger;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Plugin(name = "PluginLog4JAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class PluginLog4JAppender extends AbstractAppender {
    private static PluginLog4JAppender instance;
    private List<LogEvent> events = new ArrayList<>();

    public static PluginLog4JAppender getInstance() {
        // System.out.println("######################PluginLog4JAppender:getInstance()");
        return instance;
    }

    public static PluginLog4JAppender getInstance(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
//        System.out.println("######################PluginLog4JAppender:getInstance(4)");
        if (instance == null) {
            instance = new PluginLog4JAppender(name, filter, layout, true);
        }
        return instance;
    }

    protected PluginLog4JAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout);
//        System.out.println("######################PluginLog4JAppender:PluginLog4JAppender(3)");
    }

    protected PluginLog4JAppender(String name, Filter filter, Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
//        System.out.println("######################PluginLog4JAppender:PluginLog4JAppender(4)");
    }

    @Override
    public void append(final LogEvent event) {
        events.add(event.toImmutable());
    }

    public LogEvent getEvent(String loggerName, int index) {
        List<LogEvent> newEvents = events.stream().filter(lg -> loggerName.equals(lg.getLoggerName())).collect(Collectors.toList());
        if (newEvents.size() < index)
            return null;
        return newEvents.get(index);
    }

    public List<LogEvent> getEvents(String loggerName) {
        List<LogEvent> newEvents = events.stream().filter(lg -> loggerName.equals(lg.getLoggerName())).collect(Collectors.toList());
        return newEvents;
    }

    public List<LogEvent> getEvents(String loggerName, String methodName) {
        List<LogEvent> newEvents = events.stream().filter(
                lg -> loggerName.equals(lg.getLoggerName()) && methodName.equals(lg.getSource().getMethodName())
        ).collect(Collectors.toList());
        return newEvents;
    }

    public List<LogEvent> getEvents(long startTime, long endTime) {
        List<LogEvent> eventList = events.stream().filter(
                event -> ((startTime < event.getTimeMillis() && event.getTimeMillis() < endTime)
                        && event.getThreadId() == Thread.currentThread().getId())
        ).collect(Collectors.toList());
        return eventList;
    }

    public List<String> getMessages(String loggerName) {
        List<LogEvent> events = getEvents(loggerName);
        return getMessages(events);
    }

    public List<String> getMessages() {
        return getMessages(getEvents());
    }

    public List<String> getMessages(String loggerName, String methodName) {
        List<LogEvent> events = getEvents(loggerName, methodName);
        return getMessages(events);
    }

    public List<String> getMessages(long startTime, long endTime) {
        List<LogEvent> events = getEvents(startTime, endTime);
        return getMessages(events);
    }

    private List<String> getMessages(List<LogEvent> events) {
        return events.stream().map(logEvent -> getLayout().toSerializable(logEvent).toString()).collect(Collectors.toList());
    }

    public List<LogEvent> getEvents() {
        return events;
    }

    @PluginFactory
    public static PluginLog4JAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("otherAttribute") String otherAttribute) {

        if (name == null) {
            LOGGER.error("No name provided for PluginLog4JAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        return getInstance(name, filter, layout, true);
    }
}

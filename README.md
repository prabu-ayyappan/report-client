# dashboard-client

- build jar and push to local maven repo run task `clean build publishToMavenLocal`
- to build jar with all dependencies run task `shadowJar` 

- log4j2.xml
````xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN" packages="com.cognizant.dashboardclient.plugins.logger">
    <Appenders>
        <Console name="ConsoleAppender" target="SYSTEM_OUT">
            <PatternLayout
                    pattern="%d [%t] [%-5level] %logger{36} - %msg%n%throwable"/>
        </Console>
        <PluginLog4JAppender name="PluginLog4JAppender">
            <PatternLayout
                    pattern="%d [%t] [%-5level] %logger{36} - %msg%n%throwable"/>
        </PluginLog4JAppender>
    </Appenders>
    <Loggers>
        <Root level="DEBUG">
            <AppenderRef ref="ConsoleAppender"/>
            <AppenderRef ref="PluginLog4JAppender"/>
        </Root>
    </Loggers>
</Configuration>

````

- LEAP_MESSAGE#FILE#<FileObject>#<FileName>
````
LOGGER.info("LEAP_MESSAGE#FILE#{}#{}", ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE), "searchSelenium.png");
````
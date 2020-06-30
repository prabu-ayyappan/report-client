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

- System Properties : we can pass properties in command line arguments 

    ```properties
        -Dleap.report.output.path=C:\\Users\\Prabu.Ayyappan\\RAM\\test\\test-robot\\leap-report-output
        -Dleap.report.enabled=true
        -Dleap.report.host=http://10.120.100.56:9002/reports/
        -Dleap.report.project=TestNGDemoProject
        -Dleap.report.execution=execution-testng
        -Dleap.report.token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1ZDNlZTY2OGE1NDQxODNjNWNlM2NmNmYiLCJwZXJtaXNzaW9ucyI6ImxlYXAucGVybWlzc2lvbi5hZG1pbixsZWFwLmRhc2hib2FyZC5tYWluIiwiZW1haWwiOiJhZG1pbkBsZWFwLmNvbSIsImZpcnN0TmFtZSI6ImxlYXAiLCJsYXN0TmFtZSI6ImFkbWluIiwiYWN0aXZlIjp0cnVlLCJvd25Qcm9qZWN0SWRzIjpbIjVlY2U1MWEzYzI0ZTc0MmZkNDg1ZGE2ZiIsIjVlY2U1MjA2YzI0ZTc0MmZkNDg1ZGE3MCIsIjVlY2U1ZDg4YzI0ZTc0MmZkNDg1ZGFjMCIsIjVlY2Y5NjlmYjFjMjlhNDdkY2Q2NTA5YiIsIjVlZDBhYTY0NzdkYmMyMjFlZjQ4OGYzZiIsIjVlZDRiMWUyYjFiMTI5MGM4NTdmZmYyNyIsIjVlZDc0NWM2YjFiMTI5MGM4NTgwMDA0MCIsIjVlZDc0NWRjYjFiMTI5MGM4NTgwMDA0MSIsIjVlZDc0NWY3YjFiMTI5MGM4NTgwMDA0MiIsIjVlZDc3ZGFhYjFiMTI5MGM4NTgwMDA1OSIsIjVlZDc4NmI5YjFiMTI5MGM4NTgwMDA1YSIsIjVlZDg5OWU5YjFiMTI5MGM4NTgwMDA4NCIsIjVlZDlkNjU1YjFiMTI5MGM4NTgwMDEwYyIsIjVlZDllNjdhYjFiMTI5MGM4NTgwMDExMSIsIjVlZTc0YjlhYjFiMTI5MGM4NTgwMDI3YiIsIjVlZTc0YmJhYjFiMTI5MGM4NTgwMDI3YyIsIjVlZTlmMzRlMDA0MDUxNDkzM2VmYzg0ZSIsIjVlZTlmNzkxMDA0MDUxNDkzM2VmYzg0ZiIsIjVlZWExZjY4MDA0MDUxNDkzM2VmYzg1MCIsIjVlZjMzNjM2NTNlYWExMjkwN2NiNjczNyIsIjVlZjM1NGM0NTNlYWExMjkwN2NiNjczZCIsIjVlZjQzNGZmNTNlYWExMjkwN2NiNjczZSIsIjVlZjQ3NzVhNTNlYWExMjkwN2NiNjczZiIsIjVlZjhiNzdkOTkzYmMzMzRhNTFjZWY0OCIsIjVlZjk4ODk1YTVhNmIwN2Y5NWViNWVjMyIsIjVlZjk5YTVhOTkzYmMzMzRhNTFjZWY0OSJdLCJwcm9qZWN0SWRzIjpbIjVkMjViNzU1ZTgwMzVmMTg2NDU1MzFkZSIsIjVlY2U1MWEzYzI0ZTc0MmZkNDg1ZGE2ZiIsIjVlY2U1MjA2YzI0ZTc0MmZkNDg1ZGE3MCIsIjVlY2U1ZDg4YzI0ZTc0MmZkNDg1ZGFjMCIsIjVlY2Y5NjlmYjFjMjlhNDdkY2Q2NTA5YiIsIjVlZDBhYTY0NzdkYmMyMjFlZjQ4OGYzZiIsIjVlZDRiMWUyYjFiMTI5MGM4NTdmZmYyNyIsIjVlZDc0NWM2YjFiMTI5MGM4NTgwMDA0MCIsIjVlZDc0NWRjYjFiMTI5MGM4NTgwMDA0MSIsIjVlZDc0NWY3YjFiMTI5MGM4NTgwMDA0MiIsIjVlZDc3ZGFhYjFiMTI5MGM4NTgwMDA1OSIsIjVlZDc4NmI5YjFiMTI5MGM4NTgwMDA1YSIsIjVlZDg5OWU5YjFiMTI5MGM4NTgwMDA4NCIsIjVlZDlkNjU1YjFiMTI5MGM4NTgwMDEwYyIsIjVlZDllNjdhYjFiMTI5MGM4NTgwMDExMSIsIjVlZTc0YjlhYjFiMTI5MGM4NTgwMDI3YiIsIjVlZTc0YmJhYjFiMTI5MGM4NTgwMDI3YyIsIjVlZTlmMzRlMDA0MDUxNDkzM2VmYzg0ZSIsIjVlZTlmNzkxMDA0MDUxNDkzM2VmYzg0ZiIsIjVlZWExZjY4MDA0MDUxNDkzM2VmYzg1MCIsIjVlZjMzNjM2NTNlYWExMjkwN2NiNjczNyIsIjVlZjM1NGM0NTNlYWExMjkwN2NiNjczZCIsIjVlZjQzNGZmNTNlYWExMjkwN2NiNjczZSIsIjVlZjQ3NzVhNTNlYWExMjkwN2NiNjczZiIsIjVlZjhiNzdkOTkzYmMzMzRhNTFjZWY0OCIsIjVlZjk4ODk1YTVhNmIwN2Y5NWViNWVjMyIsIjVlZjk5YTVhOTkzYmMzMzRhNTFjZWY0OSJdLCJpYXQiOjE1OTM0MjA5NjIsImV4cCI6MTU5MzUwNzM2Mn0.j2oQGABGNg9NgrM0HylSf2lBwbhQBnYCprdJ6a9XbCY
    ```
        
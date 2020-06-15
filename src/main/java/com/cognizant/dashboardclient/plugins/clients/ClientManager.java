package com.cognizant.dashboardclient.plugins.clients;

import com.cognizant.dashboardclient.plugins.common.BaseConstants;
import com.cognizant.dashboardclient.plugins.common.TProperties;
import com.cognizant.dashboardclient.plugins.models.BaseAttachment;
import com.cognizant.dashboardclient.plugins.models.ExecutiveTestPlan;
import com.cognizant.dashboardclient.plugins.models.TestCase;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.cognizant.dashboardclient.plugins.common.BaseConstants.*;
import static com.cognizant.dashboardclient.plugins.models.StatusEnum.*;

public class ClientManager {
    private static TestReportClient testReportClient;
    private static TestReportClient uploadClient;

    public static TestReportClient getTestReportClient() {
        if (testReportClient == null) {
            TProperties properties = TProperties.getInstance();
            testReportClient = TestReportMain.getTestReportClient(properties.get(TEST_REPORT_URL));
        }
        return testReportClient;
    }

    public static TestReportClient getUploadClient() {
        if (uploadClient == null) {
            uploadClient = TestReportMain.getUploadClient();
        }
        return uploadClient;
    }

    public static BaseAttachment addImage(File file, String title) throws IOException {
        if (title == null || "".equals(title)) title = file.getName();
        String contentType = Files.probeContentType(file.toPath());
        DiskFileItem fileItem = new DiskFileItem
                ("file", contentType, false, file.getName(), (int) file.length(), file.getParentFile());
        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        Map<String, String> uploadHeaders = TestReportMain.getUploadHeaders();
        BaseAttachment baseAttachment;
        try {
            baseAttachment = getUploadClient().addImage(title, multipartFile, uploadHeaders);
        } catch (Exception e) {
            baseAttachment = new BaseAttachment();
            baseAttachment.setName(title);
            baseAttachment.setType(multipartFile.getContentType());
            e.printStackTrace();
        }
        return baseAttachment;
    }


    public static ExecutiveTestPlan pushData(ExecutiveTestPlan executiveTestPlan) {
        Map headers = TestReportMain.getHeaders();
        ExecutiveTestPlan testPlan = getTestReportClient().InsertOrUpdateTestPlan(executiveTestPlan, headers);
        leapReportOutput(testPlan.getId());
        return testPlan;
    }

    public static ExecutiveTestPlan onFinish(ExecutiveTestPlan executiveTestPlan) {
        executiveTestPlan.getTestSuites().forEach(testSuite -> {
            List<String> statusList = testSuite.getTestCases().stream().map(TestCase::getResult).collect(Collectors.toList());
            if (testSuite.getPassed().get() == testSuite.getTestsCount().get()) {
                testSuite.setResult(PASSED.name());
            } else if (statusList.contains(FAILED.name())) {
                testSuite.setResult(FAILED.name());
            } else if (statusList.contains(IGNORE.name()) || statusList.contains(SKIPPED.name())) {
                testSuite.setResult(SKIPPED.name());
            } else {
                testSuite.setResult(UNKNOWN.name());
            }

            testSuite.getTestCases().forEach(aCase -> {
                if (IN_PROGRESS.name().equals(aCase.getResult()) || IN_QUEUE.name().equals(aCase.getResult())) {
                    aCase.setResult(UNKNOWN.name());
                }
            });
        });

        return pushData(executiveTestPlan);
//        return null;
    }

    private static void leapReportOutput(String executionReportId) {

        String fileName = String.format(BaseConstants.LEAP_REPORT_OUTPUT_S_PROPERTIES, executionReportId);
        String outputPath = (String) System.getProperties().getOrDefault(LEAP_REPORT_OUTPUT_PATH, LEAP_REPORT_OUTPUT_DEFAULT_PATH);
        File file = new File(outputPath);
        file.mkdirs();

        TProperties tProperties = TProperties.getInstance();
        Properties outputProperties = new Properties();
        // set key and value
        outputProperties.setProperty(LEAP_REPORT_HOST, tProperties.get(TEST_REPORT_URL));
        outputProperties.setProperty(LEAP_REPORT_REQUEST_PATH, String.format(TEST_REPORTS_S, executionReportId));
        outputProperties.setProperty(LEAP_REPORT_REQUEST_METHOD, LEAP_REPORT_REQUEST_METHOD_GET);

        try {
            outputProperties.store(
                    new FileOutputStream(Paths.get(file.getAbsolutePath(), fileName).toString()),
                    BaseConstants.LEAP_REPORT_OUTPUT_PROPERTIES_FILE  // comments in file
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

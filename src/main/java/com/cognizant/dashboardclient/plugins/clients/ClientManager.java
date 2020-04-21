package com.cognizant.dashboardclient.plugins.clients;

import com.cognizant.dashboardclient.plugins.common.BaseConstants;
import com.cognizant.dashboardclient.plugins.common.TProperties;
import com.cognizant.dashboardclient.plugins.models.BaseAttachment;
import com.cognizant.dashboardclient.plugins.models.ExecutiveTestPlan;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

public class ClientManager {
    private static TestReportClient testReportClient;
    private static TestReportClient uploadClient;

    public static ExecutiveTestPlan pushData(ExecutiveTestPlan executiveTestPlan){
        Map headers = TestReportMain.getHeaders();
        return getTestReportClient().InsertOrUpdateTestPlan(executiveTestPlan, headers);
    }

    public static TestReportClient getTestReportClient(){
        if (testReportClient == null) {
            TProperties properties = TProperties.getInstance();
            testReportClient = TestReportMain.getTestReportClient(properties.get(BaseConstants.TEST_REPORT_URL));
        }
        return testReportClient;
    }

    public static TestReportClient getUploadClient(){
        if (uploadClient == null) {
            uploadClient = TestReportMain.getUploadClient();
        }
        return uploadClient;
    }

    public static BaseAttachment addImage(File file, String title) throws IOException {
        if (title == null || "".equals(title)) title = file.getName();
        String contentType = Files.probeContentType(file.toPath());
        DiskFileItem fileItem = new DiskFileItem
                ("file", contentType, false, file.getName(),(int) file.length(), file.getParentFile());
        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        Map<String, String> uploadHeaders = TestReportMain.getUploadHeaders();
        return getUploadClient().addImage(title, multipartFile, uploadHeaders);
    }

}

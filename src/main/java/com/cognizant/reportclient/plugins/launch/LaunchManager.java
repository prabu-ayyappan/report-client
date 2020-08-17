package com.cognizant.reportclient.plugins.launch;

import com.cognizant.reportclient.plugins.common.BaseConstants;
import com.cognizant.reportclient.plugins.common.TProperties;
import com.cognizant.reportclient.plugins.models.ExecutiveTestPlan;

import java.util.Calendar;

public class LaunchManager {

    public static ExecutiveTestPlan getInitLaunch(){
        TProperties properties = TProperties.getInstance();
        ExecutiveTestPlan testPlan = new ExecutiveTestPlan();
        testPlan.setProjectName(properties.get(BaseConstants.PROJECT_NAME));
        testPlan.setStartTime(Calendar.getInstance().getTime());
        testPlan.setName(properties.get(BaseConstants.EXECUTION_NAME));
        return testPlan;
    }

}

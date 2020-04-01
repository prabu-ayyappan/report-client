package com.cognizant.dashboardclient.plugins.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 784420 on 3/18/2020 6:57 PM
 */
public class TProperties extends HashMap<String, String> {
    private static TProperties properties ;
    private TProperties() {}
    private Map<? extends String, ? extends String> getProp(){
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream(BaseConstants.LEAP_COLLECTOR_PROPERTIES));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (Map)prop;
    }

    public static TProperties getInstance() {
        if (properties == null){
            properties = new TProperties();
            properties.putAll(properties.getProp());
        }
        return properties;
    }

}

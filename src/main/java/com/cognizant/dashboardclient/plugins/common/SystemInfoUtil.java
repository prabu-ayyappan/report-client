package com.cognizant.dashboardclient.plugins.common;

import org.apache.commons.lang3.SystemUtils;

public class SystemInfoUtil {

    enum osType {
        Windows, Linux, Unix, MacOS, Others
    }

    private static SystemInfoUtil instance;

    private String os;
    private String platform;
    private String java;

    public static SystemInfoUtil getInstance() {
        if (instance == null) {
            instance = new SystemInfoUtil();
        }
        return instance;
    }

    private SystemInfoUtil() {
        if (SystemUtils.IS_OS_WINDOWS) os = osType.Windows.name();
        else if (SystemUtils.IS_OS_LINUX) os = osType.Linux.name();
        else if (SystemUtils.IS_OS_UNIX) os = osType.Unix.name();
        else if (SystemUtils.IS_OS_MAC) os = osType.MacOS.name();
        else os = osType.Others.name();

        platform = SystemUtils.OS_ARCH;

        String javaVersion = getSystemProperty("java.specification.version", "");
        java = javaVersion;
        if (!"".equals(javaVersion) && javaVersion.contains(".")) {
            String[] split = javaVersion.split("\\.");
            if (javaVersion.startsWith("1")) {
                java = split[1];
            } else {
                java = split[0];
            }
        }
    }

    private static String getSystemProperty(final String property, final String defaultStr) {
        try {
            return System.getProperty(property);
        } catch (final SecurityException ex) {
            return defaultStr;
        }
    }

    public String getOs() {
        return os;
    }

    public String getPlatform() {
        return platform;
    }

    public String getJava() {
        return java;
    }
}

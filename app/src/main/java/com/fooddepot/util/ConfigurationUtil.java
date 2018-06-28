package com.fooddepot.util;

import java.util.Map;

/**
 * Created by ravisha on 11/25/16.
 */
public class ConfigurationUtil {

    private static Map<String,Map<String,String>>  configurationInfo;

    public static  Map<String, Map<String, String>> getConfiguration() {
        return configurationInfo;
    }

    public static void setConfiguration(Map<String, Map<String, String>> configuration) {
        configurationInfo = configuration;
    }
}

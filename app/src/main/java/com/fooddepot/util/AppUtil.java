package com.fooddepot.util;

/**
 * Created by ravisha on 12/28/16.
 */
public class AppUtil {

    public static boolean isNull(String value){
        if("null".equals(value) || null == value || "".equals(value.trim()))
            return true;
        return  false;
    }
}

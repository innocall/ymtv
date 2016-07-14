package com.lemon95.ymtv.utils;

/**
 * Created by WXT on 2016/3/3.
 * 字符串处理工具类
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param param 需要判断的字符串
     * @return true 空 false 非空
     */
    public static boolean isBlank(String param) {
        if(param == null || "".equals(param)|| "null".equals(param))
            return true;
        return false;
    }

    public static boolean isNotBlank(String param) {
        if(param == null || "".equals(param) || "null".equals(param))
            return false;
        return true;
    }

    public static boolean jsonDateIsBlank(String data) {
        if ("null".equals(data) || StringUtils.isBlank(data) || "[]".equals(data)){
            return true;
        }else{
            return false;
        }
    }
}

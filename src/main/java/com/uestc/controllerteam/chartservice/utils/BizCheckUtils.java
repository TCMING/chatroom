package com.uestc.controllerteam.chartservice.utils;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 20:41
 * @Version 1.0
 */
public class BizCheckUtils {

    public static void check(boolean flag,String message){
        if(!flag){
            throw new ChatException(message);
        }
    }

    public static void checkNull(Object obj,String message){
        if(obj == null){
            throw new ChatException(message);
        }
    }

}

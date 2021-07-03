package com.uestc.controllerteam.chartservice.utils;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 20:43
 * @Version 1.0
 */
public class ChatException extends RuntimeException{

    public ChatException(String message, Throwable e) {
        super(message, e);
    }

    public ChatException(String message) {
        super(message);
    }
}

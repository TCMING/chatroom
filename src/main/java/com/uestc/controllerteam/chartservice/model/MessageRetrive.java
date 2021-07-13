package com.uestc.controllerteam.chartservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author tianchengming
 * @Date 2021年7月3日 15:27
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class MessageRetrive {

    private String id;

    private String text;

    private String timestamp;

}
